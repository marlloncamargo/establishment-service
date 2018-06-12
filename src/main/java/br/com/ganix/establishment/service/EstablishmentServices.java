package br.com.ganix.establishment.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.common.service.constants.DateConstants;
import br.com.common.service.util.DateUtils;
import br.com.ganix.establishment.persistence.dao.AlternateScheduleDAO;
import br.com.ganix.establishment.persistence.dao.DefaultScheduleDAO;
import br.com.ganix.establishment.persistence.dao.EstablishmentDAO;
import br.com.ganix.establishment.persistence.dao.FeatureDAO;
import br.com.ganix.establishment.persistence.dao.FeatureEstablishmentDAO;
import br.com.ganix.establishment.persistence.dao.HolidayDAO;
import br.com.ganix.establishment.persistence.dao.SeasonalityDAO;
import br.com.ganix.establishment.persistence.entity.AlternateSchedule;
import br.com.ganix.establishment.persistence.entity.DefaultSchedule;
import br.com.ganix.establishment.persistence.entity.Establishment;
import br.com.ganix.establishment.persistence.entity.FeatureEstablishment;
import br.com.ganix.establishment.persistence.entity.Seasonality;
import br.com.ganix.establishment.persistence.exceptions.GanixEstablishmentPersistenceException;
import br.com.ganix.establishment.service.response.DetailEstablishmentResponse;
import br.com.ganix.establishment.service.response.EstablishmentResponse;
import br.com.ganix.establishment.service.response.SeasonalityResponse;
 
@RestController
@RequestMapping("/Establishment")
public class EstablishmentServices {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	@Qualifier("establishmentDAO")
	private EstablishmentDAO establishmentDAO; 

	@Autowired
	@Qualifier("seasonalityDAO")
	private SeasonalityDAO seasonalityDAO; 

	@Autowired
	@Qualifier("holidayDAO")
	private HolidayDAO holidayDAO; 

	@Autowired
	@Qualifier("defaultSchaduleDAO")
	private DefaultScheduleDAO defaultScheduleDAO; 
	
	@Autowired
	@Qualifier("alternateScheduleDAO")
	private AlternateScheduleDAO alternateScheduleDAO;
	
	@Autowired
	@Qualifier("featureEstablishmentDAO")
	private FeatureEstablishmentDAO featureEstablishmentDAO;
	
	@Autowired
	@Qualifier("featureDAO")
	private FeatureDAO featureDAO;
	
	@RequestMapping(value="/AddEstablishment", method=RequestMethod.POST)
    public @ResponseBody long addEstablishment(@RequestBody Establishment establishment) {
		try {
			logger.debug("Start addEstablishment(Establishment establishment)");
			return establishmentDAO.addEstablishment(new ResponseEntity<Establishment>(establishment, HttpStatus.OK).getBody());
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		} catch (GanixEstablishmentPersistenceException e) {
			e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
		return 0;
    }
	
	
	@RequestMapping(value="/DetailEstablishment/{id}", method=RequestMethod.GET)
    public @ResponseBody DetailEstablishmentResponse getDetailEstablishment(@PathVariable("id") long id) {
		logger.debug("Start getDetailEstablishment()");
		Establishment establishment = establishmentDAO.getEstablishmentWithTypesMap(id);
		List<FeatureEstablishment> features = featureEstablishmentDAO.getListFeatureEstablishmentMap(id);
		
		
		DetailEstablishmentResponse detailEstablishmentResponse = new DetailEstablishmentResponse();
		detailEstablishmentResponse.setName(establishment.getFantasyName());
		
		List<String> tags = new ArrayList<String>();
		
		for (FeatureEstablishment featureEstablishment : features) {
			tags.add(featureEstablishment.getFeature().getName());
			tags.sort(null);
		}
		
		detailEstablishmentResponse.setTags((String[]) tags.toArray());
		detailEstablishmentResponse.setDescription(establishment.getDescription());
		
		GregorianCalendar today = new GregorianCalendar(); 
		
		AlternateSchedule alternateSchedule = alternateScheduleDAO.getAlternateScheduleEstablishmentAndDate(establishment.getId(), new Date(today.getTimeInMillis()));
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		if(alternateSchedule != null) {
			detailEstablishmentResponse.setStartTime(sdf.format(new Date(alternateSchedule.getOpeningTime().getTime())));
			detailEstablishmentResponse.setEndTime(sdf.format(new Date(alternateSchedule.getClosingTime().getTime())));
			detailEstablishmentResponse.setTicketValue(alternateSchedule.getTicketValue());
		} else {
			String weekDay = "";
			
			switch (today.get(GregorianCalendar.DAY_OF_WEEK)) {
				case GregorianCalendar.SUNDAY:
					weekDay = DateConstants.DOMINGO;
					break;
			    case GregorianCalendar.MONDAY:
			    	weekDay = DateConstants.SEGUNDA_FEIRA;
			    	break;
			    case GregorianCalendar.TUESDAY:
			    	weekDay = DateConstants.TERCA_FEIRA;
			    	break;
			    case GregorianCalendar.WEDNESDAY:
			    	weekDay = DateConstants.QUARTA_FEIRA;
			    	break;
			    case GregorianCalendar.THURSDAY:
			    	weekDay = DateConstants.QUINTA_FEIRA;
			    	break;
			    case GregorianCalendar.FRIDAY:
			    	weekDay = DateConstants.SEXTA_FEIRA;
			    	break;
			    case GregorianCalendar.SATURDAY:
			    	weekDay = DateConstants.SABADO;
			    	break;
				default:
					break;
			}
			
			DefaultSchedule defaultSchedule = defaultScheduleDAO.getDefaultScheduleEstablishmentAndDayOfWeek(establishment.getId(), weekDay);
			
			detailEstablishmentResponse.setStartTime(sdf.format(new Date(defaultSchedule.getOpeningTime().getTime())));
			detailEstablishmentResponse.setEndTime(sdf.format(new Date(defaultSchedule.getClosingTime().getTime())));
			detailEstablishmentResponse.setTicketValue(defaultSchedule.getTicketValue());
		}
		
		//detailEstablishmentResponse.setDistance();
		//detailEstablishmentResponse.setDistanceUnit();
		
		return detailEstablishmentResponse;
    }

	
	@RequestMapping(value="/ListEstablishmentMap", method=RequestMethod.GET)
	public @ResponseBody List<EstablishmentResponse> getListEstablishmentMap() {
		logger.debug("Start getListEstablishmentMap()");

		List<EstablishmentResponse> list = new ArrayList<EstablishmentResponse>();
		
		List<Establishment> responseDB = establishmentDAO.getListEstablishmentMap();
		if (responseDB == null || responseDB.isEmpty()) {
			return null;
		}
		
		for (Establishment establishment : responseDB) {
			EstablishmentResponse er = new EstablishmentResponse();
			er.setCnpj(String.valueOf(establishment.getDocumentNumber()));
			er.setQuantityLimit(establishment.getQuantityLimit());
			er.setFantasyName(establishment.getFantasyName());
			list.add(er);
		}
		
		return list;
    }

	@RequestMapping(value="/ListEstablishmentName" , method=RequestMethod.GET)	
	private @ResponseBody List<String> getListEstablishmentName(String partialName){
		logger.debug("Start getListEstablishmentName(String partialName)");
		List<String> list = new ArrayList<String>();
		return list;
	}
	
	@RequestMapping(value="/UpdateQuantity/{manQuantity}/{womanQuantity}/{id}" , method=RequestMethod.GET)		
	public @ResponseBody ResponseEntity<String> updateQuantity(@PathVariable("manQuantity") Long manQuantity, 
			@PathVariable("womanQuantity") Long womanQuantity, @PathVariable("id") Long id) {
		try {
			//establishmentDAO.updateQuantity(manQuantity, womanQuantity, id);
			return new ResponseEntity<String>(HttpStatus.OK);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@RequestMapping(value="/GetFullSeasonality/{establishmentId}", method=RequestMethod.GET)
	public @ResponseBody SeasonalityResponse getFullSeasonality(@PathVariable("establishmentId") Long establishmentId){
		try {
			logger.debug("Start getFullSeasonality(Long establishmentId)");
			Seasonality seasonality = seasonalityDAO.getSeasonality(establishmentId);
			SeasonalityResponse response = new SeasonalityResponse();
			response.setEstablishment(seasonality.getEstablishment().getId());
			response.setDays(seasonality.getDays());
			response.setRegistryTime(DateUtils.getFormattedDate(seasonality.getRegistryTime()));
			
			return response;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/GetSeasonality/{establishmentId}", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Integer> getSeasonality(@PathVariable("establishmentId") Long establishmentId){
		try {
			logger.debug("Start getSeasonality(Long establishmentId)");
			Seasonality seasonality = seasonalityDAO.getSeasonality(establishmentId);			
			return new ResponseEntity<Integer>(seasonality.getDays(), HttpStatus.OK);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Method for test Service
	 * @return
	 */
	private @Value("${application.group}") String groupId;
	private @Value("${application.id}") String artifactId;
	private @Value("${application.version}") String version;
	@RequestMapping(value="/TestService", method=RequestMethod.GET)
    public @ResponseBody Object testService() {
		Map<String, String> map = new HashMap<>();
		map.put("groupId", groupId);
		map.put("artifactId", artifactId);
		map.put("version", version);
		
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}

}