package com.mathew.example.springboot.room.services.guest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Consumer;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class GuestDataService implements ApplicationListener<ApplicationReadyEvent> {

	private static Map<Long, Guest> guestData = new HashMap<Long, Guest>();

	public GuestDataService() {
		loadData();
	}

	private void loadData() {
		try {
			File file = ResourceUtils.getFile("classpath:data.sql");
			Path filePath = Paths.get(file.getPath());
			BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.US_ASCII);
			String line = null;
			long id = 1;
			while ((line = reader.readLine()) != null) {
				String dataStr = line.split("VALUES")[1];
				extractGuest(dataStr, id);
				id++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void extractGuest(String dataStr, long id) {
		StringTokenizer sTokenizer = new StringTokenizer(dataStr, ",");
		String lastNameTkn = sTokenizer.nextToken();
		String lastName = lastNameTkn.substring(2, lastNameTkn.length() - 1);

		String firstNameTkn = sTokenizer.nextToken();
		String firstName = firstNameTkn.substring(2, firstNameTkn.length() - 1);

		String emailTkn = sTokenizer.nextToken();
		String emailAddress = emailTkn.substring(2, emailTkn.length() - 1);

		String countryTkn = sTokenizer.nextToken();
		String country = countryTkn.substring(2, countryTkn.length() - 1);

		String addressTkn = sTokenizer.nextToken();
		String address = addressTkn.substring(2, addressTkn.length() - 1);

		String stateTkn = sTokenizer.nextToken();
		String state = stateTkn.substring(2, stateTkn.length() - 1);

		String phoneTkn = sTokenizer.nextToken();
		String phoneNumber = phoneTkn.substring(2, phoneTkn.length() - 1);

		Guest guest = new Guest();
		guest.setId(id);
		guest.setAddress(address);
		guest.setCountry(country);
		guest.setEmailAddress(emailAddress);
		guest.setFirstName(firstName);
		guest.setLastName(lastName);
		guest.setPhoneNumber(phoneNumber);
		guest.setState(state);
		guestData.put(id, guest);

	}

	public static void main(String[] args) {
		GuestDataService service = new GuestDataService();
		System.out.println(service.findAll().size());
		System.out.println(service.findByEmailAddress("radams1v@xinhuanet.com").getLastName());
		System.out.println(service.findById(1).getLastName());

	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println(event.getApplicationContext().getApplicationName());
		loadData();
	}

	public List<Guest> findAll() {
		return new ArrayList<>(guestData.values());
	}

	public Guest findByEmailAddress(String emailAddress) {
		Guest[] target = { null };
		guestData.values().forEach(new Consumer<Guest>() {
			public void accept(Guest g) {
				if (g.getEmailAddress().equals(emailAddress)) {
					target[0] = g;
				}
			}
		});
		return target[0];
	}

	public Guest findById(long Id) {
		return guestData.get(Id);
	}

}
