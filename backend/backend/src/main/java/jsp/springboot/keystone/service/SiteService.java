package jsp.springboot.keystone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsp.springboot.keystone.Site;
import jsp.springboot.keystone.repository.SiteRepository;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public Site getSiteById(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    public Site createSite(Site site) {
        return siteRepository.save(site);
    }

    public Site updateSite(Long id, Site updatedSite) {
        Site existing = siteRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(updatedSite.getName());
        existing.setAddress(updatedSite.getAddress());
        existing.setCity(updatedSite.getCity());
        existing.setCustomer(updatedSite.getCustomer());
        return siteRepository.save(existing);
    }

    public boolean deleteSite(Long id) {
        if (!siteRepository.existsById(id)) {
            return false;
        }
        siteRepository.deleteById(id);
        return true;
    }
}