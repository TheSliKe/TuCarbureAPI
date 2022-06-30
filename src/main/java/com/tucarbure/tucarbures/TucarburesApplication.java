package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.releves.Carburant;
import com.tucarbure.tucarbures.releves.Carburants;
import com.tucarbure.tucarbures.releves.code.CodeCarburantRepository;
import com.tucarbure.tucarbures.releves.code.CodeCarburants;
import com.tucarbure.tucarbures.releves.code.CodeCarburantsDB;
import com.tucarbure.tucarbures.security.Role;
import com.tucarbure.tucarbures.security.RoleRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tucarbure.tucarbures.StationsController.CARBURANTS;
import static com.tucarbure.tucarbures.releves.code.CodeCarburants.codeCarburantsBuilder;
import static com.tucarbure.tucarbures.releves.code.CodeCarburantsDB.codeCarburantsDBBuilder;

@SpringBootApplication
public class TucarburesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TucarburesApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, CodeCarburantRepository codeCarburantRepository) {

		return args -> {

			Role userRole = roleRepository.findByRole("USER");
			if (userRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("USER");
				roleRepository.save(newUserRole);
			}

			initStation();

			if (codeCarburantRepository.count() == 0) {
				List<CodeCarburantsDB> codeCarburants = new ArrayList<CodeCarburantsDB>() {
					{
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("SP95").code("E5").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("SP98").code("E5").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("SP95-E10").code("E10").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("Super Ethanol").code("E85").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("Gazole").code("B7").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("Gazole EMAG").code("B10").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("Gazole Paraffinique").code("XTL").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("Hydrogène").code("H2").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("GPL-C").code("LPG").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("Gaz Naturel Comprimé").code("GNC").build());
						add(codeCarburantsDBBuilder().id(UUID.randomUUID()).nom("Gaz Naturel Liquéfié").code("GNL").build());
					}
				};

				codeCarburantRepository.saveAll(codeCarburants);
			}

			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
			}
		};

	}

	@Autowired
	private StationService stationService;

	private void initStation(){

		if (stationService.countStation() == 0){

			final String uri = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=prix-des-carburants-j-1&q=&rows=10000";

			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(uri, String.class);

			JSONObject jsonObject = new JSONObject(result);

			JSONArray jsonArray = (JSONArray) jsonObject.get("records");

			jsonArray.forEach(item -> {

				JSONObject obj = (JSONObject) item;
				JSONObject fields = (JSONObject) obj.get("fields");

				if (obj.has("geometry")){
					JSONObject geometry = (JSONObject) obj.get("geometry");

					if (fields.has("name") && fields.has("address") && fields.has("cp") && fields.has("com_arm_name")){
						System.out.println("save" + fields.getString("name"));

						if (fields.has("fuel")){

							List<Carburant> list = new ArrayList<>();
							String[] fuel = fields.getString("fuel").split("/");
							String[] fuelFinal = new String[fuel.length];
							for (int i = 0; i < fuel.length; i++) {

								String nomCarburant;
								switch (fuel[i]) {
									case "SP95" -> nomCarburant = "SP95";
									case "SP98" -> nomCarburant = "SP98";
									case "E10" -> nomCarburant = "SP95-E10";
									case "E85" -> nomCarburant = "Super Ethanol";
									case "Gazole" -> nomCarburant = "Gazole";
									case "GPLc" -> nomCarburant = "GPL-C";
									default -> {
										System.out.println(fuel[i]);
										nomCarburant = "oups";
									}
								}
								fuelFinal[i] = CARBURANTS.get(nomCarburant);
								list.add(Carburant.builder()
										.nom(nomCarburant)
										.codeEuropeen(CARBURANTS.get(nomCarburant))
										.disponible(true)
										.build()
								);
							}

							String[] shortage = new String[0];
							String[] shortageFinal = new String[0];
							if (fields.has("shortage")){
								shortage = fields.getString("shortage").split("/");
								shortageFinal = new String[shortage.length];

								for (int i = 0; i < shortage.length; i++) {

									String nomCarburant;
									switch (shortage[i]) {
										case "SP95" -> nomCarburant = "SP95";
										case "SP98" -> nomCarburant = "SP98";
										case "E10" -> nomCarburant = "SP95-E10";
										case "E85" -> nomCarburant = "Super Ethanol";
										case "Gazole" -> nomCarburant = "Gazole";
										case "GPLc" -> nomCarburant = "GPL-C";
										default -> {
											System.out.println(shortage[i]);
											nomCarburant = "oups";
										}
									}
									shortageFinal[i] = CARBURANTS.get(nomCarburant);
									list.add(Carburant.builder()
											.nom(nomCarburant)
											.codeEuropeen(CARBURANTS.get(nomCarburant))
											.disponible(false)
											.build()
									);
								}
							}

							Carburants carburants = Carburants.builder()
									.listeCarburants(ArrayUtils.addAll(fuelFinal, shortageFinal))
									.details(list)
									.build();

							stationService.saveStation(Station.builder()
									.marque(Marque.builder()
											.nom(fields.getString("name"))
											.description(fields.getString("name")).build())
									.adresse(Adresse.builder()
											.rue(fields.getString("address"))
											.codePostal(fields.getString("cp"))
											.ville(fields.getString("com_arm_name"))
											.latitude(geometry.getJSONArray("coordinates").getDouble(1))
											.longitude(geometry.getJSONArray("coordinates").getDouble(0))
											.build())
									.carburants(carburants)
									.build()
							);

						}

					}

				}

			});

		}


	}

}
