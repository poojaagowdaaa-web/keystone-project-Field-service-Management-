package jsp.springboot.keystone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jsp.springboot.keystone.Customer;
import jsp.springboot.keystone.Site;
import jsp.springboot.keystone.SiteDTO;
import jsp.springboot.keystone.repository.CustomerRepository;
import jsp.springboot.keystone.service.SiteService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Site> getAllSites() {
        return siteService.getAllSites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable Long id) {
        Site site = siteService.getSiteById(id);
        if (site == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(site);
    }

    @PostMapping
    public ResponseEntity<Site> createSite(@Valid @RequestBody SiteDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }

        Site site = new Site();
        site.setName(dto.getName());
        site.setAddress(dto.getAddress());
        site.setCity(dto.getCity());
        site.setCustomer(customer);

        return ResponseEntity.ok(siteService.createSite(site));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable Long id, @Valid @RequestBody SiteDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId()).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }

        Site site = new Site();
        site.setName(dto.getName());
        site.setAddress(dto.getAddress());
        site.setCity(dto.getCity());
        site.setCustomer(customer);

        Site updated = siteService.updateSite(id, site);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        boolean deleted = siteService.deleteSite(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}