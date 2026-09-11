package jsp.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@GetMapping("/home")
	public String homepage() {
		return "Welcome to Home Page";
		
	}
	@GetMapping("/student")
	public String getStudent(@RequestParam int id,@RequestParam String name) {
		return "ID :"+id + "\nname :"+name;
	}
	@GetMapping("/employee/{role}/{salary}")
	public String getemployee(@PathVariable String role,@PathVariable double salary) {
		return "role :"+role+ "\ssalary :"+salary;
	}
	
		@GetMapping("/product")
		public String getproduct(@RequestHeader int id,@RequestHeader String name,@RequestHeader int id1,@RequestHeader String name1) {
			return "id :"+id+"\nname :"+name +"id1 :"+id1+"\nname1 :"+name1;
		}
	
}
