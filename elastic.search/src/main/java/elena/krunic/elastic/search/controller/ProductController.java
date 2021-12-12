package elena.krunic.elastic.search.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import antlr.StringUtils;
import elena.krunic.elastic.search.dto.ProductDTO;
import elena.krunic.elastic.search.dto.StringResponseDTO;
import elena.krunic.elastic.search.model.Product;
import elena.krunic.elastic.search.model.Seller;
import elena.krunic.elastic.search.model.User;
import elena.krunic.elastic.search.repository.ProductRepository;
import elena.krunic.elastic.search.repository.UserRepository;
import elena.krunic.elastic.search.service.ProductService;
import elena.krunic.elastic.search.service.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

@RestController
@RequestMapping(value="/api/products")
@CrossOrigin
public class ProductController {
	
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired 
    ServletContext servletContext; 
    
    private TemplateEngine templateEngine; 
    
	@Autowired 
	private UserRepository userRepository; 
	
	@Autowired 
	private UserService userService; 
	
	@Autowired
	private ProductRepository productRepository; 
	
	@Autowired 
	private ProductService productService; 
	
	public ProductController(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine; 
	}
	
	@RequestMapping(path="/pdf/{id}") 
	public ResponseEntity<?> getPDF(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Product product = productRepository.getById(id);
		
		WebContext context = new WebContext(request,response, servletContext); 
		context.setVariable("productEntry", product);
		String productHTML = templateEngine.process("product", context);
		
		 ByteArrayOutputStream target = new ByteArrayOutputStream();
	     ConverterProperties converterProperties = new ConverterProperties();
	     converterProperties.setBaseUri("http://localhost:8080");
	     
	     HtmlConverter.convertToPdf(productHTML, target, converterProperties);
	     
	     byte[] bytes = target.toByteArray();
	     
	     return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product.pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(bytes);

	}
	
	@GetMapping(value="/all")
	public ResponseEntity<List<ProductDTO>> getProducts(){
		List<Product> products = productRepository.findAll(); 
		List<ProductDTO> productsDTO = new ArrayList<>();
		
		for(Product product: products) {
			productsDTO.add(new ProductDTO(product));
		}
		
        logger.debug("Products dto list : {}", () -> productsDTO);

		return new ResponseEntity<List<ProductDTO>>(productsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id) {
		Product product = productRepository.getById(id); 
		
		if(product == null) {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProductDTO>(new ProductDTO(product), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json", value="/saveProduct")
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
		
		Product product = new Product(); 
		Seller user = (Seller) userRepository.getById(product.getSeller().getId());
		
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPath(productDTO.getPath());
		product.setPrice(productDTO.getPrice());
		product.setSeller(user);
		
		product = productRepository.save(product); 
		
		return new ResponseEntity<>(new ProductDTO(product), HttpStatus.CREATED); 
	}
	
	@PutMapping(value="/updateProduct/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id) {
		Product product = productRepository.getById(id); 
		
		if(product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPath(productDTO.getPath());
		product.setPrice(productDTO.getPrice());
		
		product = productRepository.save(product); 
		
		return new ResponseEntity<>(new ProductDTO(product), HttpStatus.OK); 
	}
	
	
	@DeleteMapping(value="/deleteProduct/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
		Product product = productRepository.getById(id); 
		
		if(product != null) {
			productRepository.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK); 
		}
		
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/orderProduct")
	public ResponseEntity<?> order(@RequestBody ProductDTO productDTO, Principal principal) {
		try {
			String message = productService.orderProduct(productDTO, "elenakrunic@gmail.com");
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	//=======================================================================================
	
	@PostMapping(consumes="application/json", value="/saveProductTest")
	public ResponseEntity<ProductDTO> saveProductTest(@RequestBody ProductDTO productDTO) {
		
		Product product = new Product(); 
		
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setPath(productDTO.getPath());
		
		product = productRepository.save(product); 
		
		return new ResponseEntity<>(new ProductDTO(product), HttpStatus.CREATED); 
	}
	
	@GetMapping(value="/allTest")
	public ResponseEntity<List<ProductDTO>> getProductsTest(){
		List<Product> products = productRepository.findAll(); 
		List<ProductDTO> productsDTO = new ArrayList<>();
		
		for(Product product: products) {
			productsDTO.add(new ProductDTO(product));
		}
		
        //logger.debug("Products dto list : {}", () -> productsDTO);
		return new ResponseEntity<List<ProductDTO>>(productsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="getOneTest/{id}")
	public ResponseEntity<ProductDTO> getProductTest(@PathVariable("id") Long id) {
		Product product = productRepository.getById(id); 
		
		if(product == null) {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProductDTO>(new ProductDTO(product), HttpStatus.OK);
	}
	
	@PutMapping(value="/updateProductTest")
	//ovde kad radim update traziti principal.getName od seller-a 
	public ResponseEntity<ProductDTO> updateProductTest(@RequestBody ProductDTO productDTO, Principal principal) {
		
		Product product = productRepository.findByName("Retina MacBook Pro 13 inch MF841"); 
		
		if(product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPath(productDTO.getPath());
		product.setPrice(productDTO.getPrice());
		
		product = productRepository.save(product); 
		
		return new ResponseEntity<>(new ProductDTO(product), HttpStatus.OK); 
	}
	
	 @GetMapping("/oneTestPrincipal")
	 public ResponseEntity<?> getOneTestPrincipal(Principal principal) {
		
	        try {
	            ProductDTO product = productService.getOne(principal.getName());
	            return new ResponseEntity<>(product, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
	        }
	    }
	 
	@DeleteMapping(value="/deleteProductTest/{id}")
    public ResponseEntity<Void> deleteProductTest(@PathVariable("id") Long id) {
			Product product = productRepository.getById(id); 
			
			if(product != null) {
				productRepository.deleteById(id);
				//System.out.println(" >>>>>>>>>>>>>>>>>>>>>> USPJESNO OBRISAN PROIZVOD >>>>>>>>>>>>>>>>>");
				return new ResponseEntity<Void>(HttpStatus.OK); 
			}
			
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	
	
}
