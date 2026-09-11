import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import client from '../api/client';
import { useAuth } from '../context/AuthContext';

const ROLES = [
    { value: 'MANAGER', label: 'Manager', desc: 'Create and manage your team workspace', icon: '👔' },
    { value: 'DISPATCHER', label: 'Dispatcher', desc: 'Join your team with an invite code', icon: '📋' },
    { value: 'TECHNICIAN', label: 'Technician', desc: 'Join your team with an invite code', icon: '🔧' },
    { value: 'CUSTOMER', label: 'Customer', desc: 'Track your service requests', icon: '🏢' },
];

function PasswordStrength({ password }) {
    const checks = [
        { label: '8+ characters', pass: password.length >= 8 },
        { label: 'Uppercase', pass: /[A-Z]/.test(password) },
        { label: 'Lowercase', pass: /[a-z]/.test(password) },
        { label: 'Number', pass: /[0-9]/.test(password) },
    ];
    const score = checks.filter(c => c.pass).length;
    const colors = ['#ef4444', '#f97316', '#eab308', '#22c55e'];
    if (!password) return null;
    return (
        <div style={{ marginTop: 8 }}>
            <div style={{ display: 'flex', gap: 4, marginBottom: 6 }}>
                {[1, 2, 3, 4].map(i => (
                    <div key={i} style={{
                        flex: 1, height: 3, borderRadius: 2,
                        background: i <= score ? colors[score - 1] : 'rgba(255,255,255,0.1)',
                        transition: 'background 0.3s'
                    }} />
                ))}
            </div>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: 8 }}>
                {checks.map(c => (
                    <span key={c.label} style={{ fontSize: 11, color: c.pass ? '#22c55e' : '#8892a4' }}>
                        {c.pass ? '✓' : '○'} {c.label}
                    </span>
                ))}
            </div>
        </div>
    );
}

export default function Register() {
    const [step, setStep] = useState(1);
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [role, setRole] = useState('');
    const [organizationName, setOrganizationName] = useState('');
    const [inviteCode, setInviteCode] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const { login } = useAuth();
    const navigate = useNavigate();

    const passwordValid = password.length >= 8 && /[A-Z]/.test(password) && /[a-z]/.test(password) && /[0-9]/.test(password);

    const inputStyle = {
        width: '100%', padding: '12px 14px',
        background: 'rgba(255,255,255,0.07)',
        border: '1px solid rgba(255,255,255,0.12)',
        borderRadius: 10, color: 'white', fontSize: 14,
        outline: 'none', boxSizing: 'border-box', fontFamily: 'inherit'
    };

    const labelStyle = {
        display: 'block', color: '#b0bac9', fontSize: 13, fontWeight: 500, marginBottom: 7
    };

    const handleStep1 = (e) => {
        e.preventDefault();
        setError('');
        if (!name.trim()) { setError('Name is required'); return; }
        if (!email.trim()) { setError('Email is required'); return; }
        if (!passwordValid) { setError('Password must meet all requirements'); return; }
        setStep(2);
    };

    const handleRoleSelect = (r) => {
        setRole(r);
        setStep(3);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        if (role === 'MANAGER' && !organizationName.trim()) {
            setError('Organization name is required'); return;
        }
        if ((role === 'DISPATCHER' || role === 'TECHNICIAN') && !inviteCode.trim()) {
            setError('Invite code is required'); return;
        }

        setLoading(true);
        try {
            const response = await client.post('/auth/register', {
                name,
                email,
                password,
                role,
                organizationName: role === 'MANAGER' ? organizationName : undefined,
                inviteCode: role !== 'MANAGER' ? inviteCode : undefined,
            });

            login(response.data.token);
            navigate('/dashboard');
        } catch (err) {
            setError(err.response?.data?.message || 'Registration failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return {
        // Return component structure or JSX depending on standard React usage
        ROLES,
        PasswordStrength,
        Register
    };
}