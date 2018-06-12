package br.com.ganix.establishment.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import br.com.ganix.establishment.persistence.entity.Establishment;
import br.com.ganix.establishment.service.StartService;
import br.com.ganix.establishment.service.response.DetailEstablishmentResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StartService.class)
@WebIntegrationTest
public class EstablishmentServicesTest {

	private RestTemplate restTemplate = new TestRestTemplate();
	private String serviceLocation = "http://localhost:8080/API/Establishment";
	
	@Test
	public void testAddEstablishment() {
		Establishment establishment = new Establishment();
		establishment.setDocumentNumber(0L);
		establishment.setCompanyName("Test company name");
		establishment.setFantasyName("Test fantasy name");
		establishment.setQuantityLimit(300);
		establishment.setViewerId("mobile");
		establishment.setDescription("Lorem ipsum dolor sit amet, consectetur "
				+ "adipiscing elit. Proin vel aliquam nibh. Vestibulum aliquet "
				+ "erat massa, ac interdum tellus ultricies vel. Aenean lacus "
				+ "augue, blandit at mollis id, egestas et mi. Quisque tempus, "
				+ "ex ac fermentum aliquam, turpis sed.");
		
		ResponseEntity<Long> id = restTemplate.postForEntity(serviceLocation + "/AddEstablishment", establishment, Long.class);
		assertTrue(id.getBody() > 0);
	}

	@Test
	public void testGetDetailEstablishment() {
		ResponseEntity<DetailEstablishmentResponse> response = restTemplate.getForEntity(serviceLocation + "/DetailEstablishment", DetailEstablishmentResponse.class);
		assertNotNull(response);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetListEstablishmentMap() {
		ResponseEntity<List> response = restTemplate.getForEntity(serviceLocation + "/ListEstablishmentMap", List.class); //new ParameterizedTypeReference<List<EstablishmentResponse>>() {});
		assertFalse(response.getBody().isEmpty());
	}

}
