package es.udc.rs.telco.client.ui;

import es.udc.rs.telco.client.service.ClientTelcoService;
import es.udc.rs.telco.client.service.ClientTelcoServiceFactory;
import es.udc.rs.telco.client.service.dto.ClientCustomerDto;
import es.udc.rs.telco.client.service.dto.ClientPhoneCallDto;
import es.udc.rs.telco.client.service.rest.dto.PhoneCallStatus;
import es.udc.rs.telco.client.service.rest.dto.PhoneCallType;
import es.udc.rs.telco.client.service.rest.exceptions.ClientMonthNotDoneException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TelcoServiceClient {

	public static void main(String[] args) {

		if (args.length == 0) {
			printUsageAndExit();
		}
		ClientTelcoService clientTelcoService = ClientTelcoServiceFactory.getService();
		if ("-addClient".equalsIgnoreCase(args[0])) {
			validateArgs(args, 5, new int[] {});

			// [-addCustomer] TelcoServiceClient -addClient <name> ...

			try {
				Long clientId = clientTelcoService.addClient(new ClientCustomerDto(null,
						args[1], args[2], args[3], args[4] )); // Invoke method from the clientTelcoService
				System.out.println("Client " + clientId + " " + "created successfully");
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		} else if ("-addPhoneCall".equalsIgnoreCase(args[0])) {
            //[-addPhoneCall] <customerId> <startDate> <duration> <destinationNumber> <phoneCallType>
            validateArgs(args, 6, new int[]{1, 3});

			PhoneCallType callType;
			if (args[5].equals("LOCAL")) {
				callType = PhoneCallType.LOCAL;
			} else if (args[5].equals("NATIONAL")) {
				callType = PhoneCallType.NATIONAL;
			} else {
				callType = PhoneCallType.INTERNATIONAL;
			}
			try {
				Long phoneCallId = clientTelcoService.addPhoneCall(new ClientPhoneCallDto( null, Long.parseLong(args[1]),
						LocalDateTime.parse(args[2]), Long.parseLong(args[3]), args[4],  callType, PhoneCallStatus.PENDING));
				System.out.println("PhoneCall " + phoneCallId + " created successfully");
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}



        } else if ("-removeClient".equalsIgnoreCase(args[0])) {
				validateArgs(args, 2, new int[] {1});

			try {
				clientTelcoService.deleteClient(Long.parseLong(args[1]));
				System.out.println("Client " + Long.parseLong(args[1]) + " " + "removed successfully");
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}


		} else if ("-updateStatus".equalsIgnoreCase(args[0])){
			// -updateStatus <customerId> <year> <month>
			validateArgs(args, 4, new int[]{1,2,3});
			try {
				clientTelcoService.updateStatus(Long.parseLong(args[1]), Integer.parseInt(args[2]),
						Integer.parseInt(args[3]));
				System.out.println("[+]Calls state correctly updated");
			} catch (InstanceNotFoundException e) {
				printErrorAndExit(e.getLocalizedMessage());
			}catch (ClientMonthNotDoneException e) {
				printErrorAndExit(e.getLocalizedMessage());
			} catch (InputValidationException e) {
				printErrorAndExit(e.getLocalizedMessage());
			}
		} else if ("-findPhoneCalls".equalsIgnoreCase(args[0])){
			//[-findPhoneCalls] <customerId> <startDate> <endDate> <phoneCallType> <startRange> <size>
			String type = "null";
			Integer starti = null;
			Integer endi = null;
			if(args.length == 4) {
				validateArgs(args, 4, new int[]{1});
			}else if(args.length == 5){
				type = args[4];
				validateArgs(args, 5, new int[] {1});
			}else if(args.length == 6){
				starti = Integer.valueOf(args[4]);
				endi = Integer.valueOf(args[5]);
				validateArgs(args, 6, new int[] {1,4,5});
			}else if(args.length == 7) {
				type = args[4];
				starti = Integer.valueOf(args[5]);
				endi = Integer.valueOf(args[6]);
				validateArgs(args, 7, new int[]{1, 5, 6});
			} else {
				printUsageAndExit();
			}
			try {
				List<ClientPhoneCallDto> resultCalls = new ArrayList<>();
				resultCalls = clientTelcoService.findPhoneCallInterval(
						Long.parseLong(args[1]),
						LocalDateTime.parse(args[2]).toString(),
						LocalDateTime.parse(args[3]).toString(),
						type,starti,endi);
				System.out.println("[+]PhoneCall List: ");
				for (ClientPhoneCallDto call : resultCalls){
					System.out.println(call.toString());
				}
			} catch (InstanceNotFoundException e) {
				printErrorAndExit(e.getLocalizedMessage());
			} catch (InputValidationException e) {
				printErrorAndExit(e.getLocalizedMessage());
			} catch (Exception e) {
			     e.printStackTrace(System.err);
		    }
		} else {
			printUsageAndExit();
		}
	}

	public static void validateArgs(String[] args, int expectedArgs, int[] numericArguments) {
		if (expectedArgs != args.length) {
			printUsageAndExit();
		}
		for (int i = 0; i < numericArguments.length; i++) {
			int position = numericArguments[i];
			try {
				Double.parseDouble(args[position]);
			} catch (NumberFormatException n) {
				printUsageAndExit();
			}
		}
	}

	public static void printUsageAndExit() {
		printUsage();
		System.exit(-1);
	}

	public static void printErrorAndExit(String error){
		System.err.println(error);
		System.exit(-1);
	}

	public static void printUsage() {
		System.err.println(
				"Usage:\n" + "    [-addClient]    TelcoServiceClient -addClient <name> ...\n" +
		                     "    [-findClient]   TelcoServiceClient -findClient <clientId>\n" +
						     "    [-removeClient]   TelcoServiceClient -removeClient <clientId>\n" +
						     "...\n");
	}

}
