import React, { useEffect, useState } from 'react';

const STATUSES = ['NEW', 'ASSIGNED', 'IN_PROGRESS', 'ON_HOLD', 'COMPLETED', 'CANCELLED', 'CLOSED'];

function KanbanBoard() {
    const [workOrders, setWorkOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/api/workorders')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch work orders');
                }
                return response.json();
            })
            .then((data) => {
                setWorkOrders(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <p>Loading work orders...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    const filteredOrders = workOrders.filter((wo) =>
        wo.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div style={{ padding: '20px' }}>
            <h1>KEYSTONE Work Order Board</h1>

            <input
                type="text"
                placeholder="Search by title..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{
                    padding: '8px 12px',
                    marginBottom: '16px',
                    width: '300px',
                    border: '1px solid #ccc',
                    borderRadius: '6px',
                    fontSize: '14px',
                }}
            />

            <div style={{ display: 'flex', gap: '16px', overflowX: 'auto' }}>
                {STATUSES.map((status) => {
                    const ordersInStatus = filteredOrders.filter((wo) => wo.status === status);
                    return (
                        <div
                            key={status}
                            style={{
                                minWidth: '220px',
                                background: '#f4f4f4',
                                borderRadius: '8px',
                                padding: '12px',
                            }}
                        >
                            <h3>{status.replace('_', ' ')}</h3>
                            {ordersInStatus.length === 0 && (
                                <p style={{ color: '#999', fontSize: '14px' }}>No work orders</p>
                            )}
                            {ordersInStatus.map((wo) => (
                                <div
                                    key={wo.id}
                                    style={{
                                        background: 'white',
                                        border: '1px solid #ddd',
                                        borderRadius: '6px',
                                        padding: '10px',
                                        marginBottom: '8px',
                                    }}
                                >
                                    <strong>{wo.title}</strong>
                                    <p style={{ fontSize: '13px', color: '#555' }}>{wo.description}</p>
                                </div>
                            ))}
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default KanbanBoard;