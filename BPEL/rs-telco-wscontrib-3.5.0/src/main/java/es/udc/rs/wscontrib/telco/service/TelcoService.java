package es.udc.rs.wscontrib.telco.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jakarta.annotation.PostConstruct;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(name = "TelcoProvider", serviceName = "TelcoProviderService", targetNamespace = "http://rs.udc.es/telco")
public class TelcoService {

    // Note: These mock implementation assumes current year when current
    // month >7 and previous year for month > 8
    private static List<PhoneCall> callsCustomer2Month9 = new ArrayList<PhoneCall>();
    private static List<PhoneCall> callsCustomer2Month8 = new ArrayList<PhoneCall>();
    private static List<PhoneCall> callsCustomer2Month7 = new ArrayList<PhoneCall>();

    private static List<PhoneCall> callsCustomer1Month9 = new ArrayList<PhoneCall>();
    private final static SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {

        try {
            Integer year = getTargetYear();

            // Customer 2, month 9
            PhoneCall phoneCall1 = new PhoneCall(1L, 2L, calFromString(year, "09-11 11:00:00:00"), 3000L, "+34981999999", PhoneCallType.LOCAL);
            PhoneCall phoneCall2 = new PhoneCall(2L, 2L, calFromString(year, "09-12 12:00:00:00"), 2000L, "+34091600000", PhoneCallType.NATIONAL);
            PhoneCall phoneCall3 = new PhoneCall(3L, 2L, calFromString(year, "09-13 13:00:00:00"), 500L, "+44666333333", PhoneCallType.INTERNATIONAL);
            callsCustomer2Month9.add(phoneCall1);
            callsCustomer2Month9.add(phoneCall2);
            callsCustomer2Month9.add(phoneCall3);
            System.err.println("added: " + callsCustomer2Month9);

            // Customer 2, month 8
            phoneCall1 = new PhoneCall(4L, 2L, calFromString(year, "08-01 01:00:00:00"), 4000L, "+34981999999", PhoneCallType.LOCAL);
            phoneCall2 = new PhoneCall(5L, 2L, calFromString(year, "08-02 02:00:00:00"), 800L, "+34091600000", PhoneCallType.NATIONAL);
            phoneCall3 = new PhoneCall(6L, 2L, calFromString(year, "08-03 03:00:00:00"), 400L, "+44666333333", PhoneCallType.INTERNATIONAL);
            callsCustomer2Month8.add(phoneCall1);
            callsCustomer2Month8.add(phoneCall2);
            callsCustomer2Month8.add(phoneCall3);

            // Customer 2, month 7
            phoneCall1 = new PhoneCall(7L, 2L, calFromString(year, "07-04 04:00:00:00"), 5000L, "+34981999999", PhoneCallType.LOCAL);
            phoneCall2 = new PhoneCall(8L, 2L, calFromString(year, "07-05 05:00:00:00"), 600L, "+34091600000", PhoneCallType.NATIONAL);
            phoneCall3 = new PhoneCall(9L, 2L, calFromString(year, "07-06 06:00:00:00"), 200L, "+44666333333", PhoneCallType.INTERNATIONAL);
            callsCustomer2Month7.add(phoneCall1);
            callsCustomer2Month7.add(phoneCall2);
            callsCustomer2Month7.add(phoneCall3);

            // Customer 1, month 9
            phoneCall1 = new PhoneCall(10L, 1L, calFromString(year, "09-21 21:00:00:00"), 5000L, "+34981999999", PhoneCallType.LOCAL);
            phoneCall2 = new PhoneCall(11L, 1L, calFromString(year, "09-22 22:00:00:00"), 1000L, "+34091600000", PhoneCallType.NATIONAL);
            phoneCall3 = new PhoneCall(12L, 1L, calFromString(year, "09-23 23:00:00:00"), 400L, "+44666333333", PhoneCallType.INTERNATIONAL);
            callsCustomer1Month9.add(phoneCall1);
            callsCustomer1Month9.add(phoneCall2);
            callsCustomer1Month9.add(phoneCall3);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public TelcoService() {
    }

    @PostConstruct()
    private void init() {
    }

    @WebMethod(operationName = "findCallsToBill")
    public List<PhoneCall> findCallsToBill(@WebParam(name = "customerId") Long customerId,
                                           @WebParam(name = "month")    Byte month,
                                           @WebParam(name = "year")     Integer year) throws TelcoException {

        System.out.println("findCallsToBill\n  input parameters" + "\n    customerId: "
				+ customerId + "\n    month: " + month + "\n    year:" + year);

        if (customerId == null || month == null || year == null) {
            throw new TelcoException("Cannot obtain calls to bill for customer \"" + customerId + "\"");
        }

        List<PhoneCall> callsToReturn = getCallsToBill(customerId, month, year);

        System.out.println("  return value" + "\n    " + callsToReturn);

        return callsToReturn;
    }


    @WebMethod(operationName = "changeStatus")
    public void changeStatus(@WebParam(name = "customerId")Long customerId,
                             @WebParam(name = "month") Byte month,
                             @WebParam(name = "year") Integer year,
                             @WebParam(name = "newPhoneCallStatus") PhoneCallStatus newPhoneCallStatus) throws TelcoException {

        System.out.println("changeStatus\n  input parameters" + "\n    customerId: "
                + customerId + "\n    month: " + month + "\n    year:" + year + "\n    newPhoneCallStatus:" + newPhoneCallStatus);

        if (customerId == null || month == null || year == null || newPhoneCallStatus == null) {
            throw new TelcoException("Cannot change status of calls for customer \"" + customerId +
                    "\" (" + month + ", " + year + ", " + newPhoneCallStatus + ")");
        }

        List<PhoneCall> phoneCalls = getCallsToBill(customerId, month, year);

        boolean changeAllowed = true;
        switch (newPhoneCallStatus) {
            case PENDING:
                changeAllowed = false;
                break;
            case BILLED:
                changeAllowed = checkStatus(phoneCalls, PhoneCallStatus.PENDING);
                break;
            case PAID:
                changeAllowed = checkStatus(phoneCalls, PhoneCallStatus.BILLED);
                break;
        }
        if (!changeAllowed) {
            throw new TelcoException("Change not allowed for customer \"" + customerId +
                    "\" (" + month + ", " + year + ", " + newPhoneCallStatus + ")");
        }

        phoneCalls.stream().forEach(phoneCall -> phoneCall.setPhoneCallStatus(newPhoneCallStatus));
        System.out.println("  return value" + "\n    <empty>");

    }

    private static Calendar calFromString(Integer year, String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(simpleDateFormatter.parse(year + "-" + date));
        return cal;
    }

    private static Integer getTargetYear() {
        LocalDateTime now = LocalDateTime.now();
        Integer year = now.getYear();
        if (now.getMonthValue() <= 9) {
            year = year - 1;
        }
        return year;
    }

    private static List<PhoneCall> getCallsToBill (Long customerId, Byte month, Integer year) {
        List<PhoneCall> callsToReturn = null;
        if (year.equals(getTargetYear())) {
            if (customerId == 2L) {
                switch (month) {
                    case 9: callsToReturn = callsCustomer2Month9; System.out.println("assigned" + callsToReturn); break;
                    case 8: callsToReturn = callsCustomer2Month8; break;
                    case 7: callsToReturn = callsCustomer2Month7; break;
                }
            } else {
                if (customerId == 1L && month == 9) {
                    callsToReturn = callsCustomer1Month9;
                }
            }
        }
        return callsToReturn;
    }

    private static boolean checkStatus(List<PhoneCall> phoneCalls, PhoneCallStatus allowedPhoneCallStatus) {
        if (phoneCalls==null) {
            return false;
        }
        return phoneCalls.stream().allMatch(phoneCall -> phoneCall.getPhoneCallStatus() == allowedPhoneCallStatus);
    }

}
