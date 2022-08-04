package es.udc.rs.telco.test.model.telcoservice;

import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.exceptions.InvalidCallStateException;
import es.udc.rs.telco.model.exceptions.MonthNotDoneException;
import es.udc.rs.telco.model.exceptions.CallsNotEmptyException;

import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.rs.telco.model.telcoservice.MockTelcoService;
import es.udc.rs.telco.model.telcoservice.TelcoService;
import es.udc.rs.telco.model.telcoservice.TelcoServiceFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TelcoServiceTest {

    private static TelcoService telcoService = null;

    private void deleteCalls() {
        MockTelcoService telco = (MockTelcoService) telcoService;
        telco.delCalls();
    }


    @BeforeAll
    public static void init() {
        telcoService = TelcoServiceFactory.getService();

    }

    @Test
    public void testAddFindCustomer() throws InstanceNotFoundException, InputValidationException, CallsNotEmptyException {

        Customer c = telcoService.addCustomer("Jose Gonzalez", "10203040E0", "Calle 123", "666555444");
        assertEquals(c, telcoService.findClientById(c.getCustomerId()));
        telcoService.delCustomer(c.getCustomerId());
        c = null;
    }

    @Test
    public void testAddCustomerSamePhone() throws InputValidationException, InstanceNotFoundException, CallsNotEmptyException {
        String expectedMessage = "The phone number already exists!";
        Customer c = telcoService.addCustomer("Pepe Fernandez", "10203040E", "Calle 123", "666555444");
        try {
            Customer c1 = telcoService.addCustomer("Juan Manuel", "14243444Z", "Calle 222", "666555444");
        } catch (InputValidationException e) {
            assertTrue(e.toString().contains(expectedMessage), e.toString());
        } finally {
            //deleteCalls();
            //telcoService.delCustomer(c.getCustomerId());
            c = null;
        }
    }

    @Test
    public void testDelCustomer() throws InstanceNotFoundException, InputValidationException, CallsNotEmptyException{
        Customer c = telcoService.addCustomer("Fernando", "15254545F", "Calle 1", "606905404");
        Customer find = telcoService.findClientById(c.getCustomerId());
        telcoService.delCustomer(c.getCustomerId());
        try {
            Customer find2 = telcoService.findClientById(c.getCustomerId());
        } catch (InstanceNotFoundException e) {
            assertTrue(true);
        }
        c = null;
        find = null;
    }

    @Test
    public void testUpdateCustomer() throws InstanceNotFoundException, InputValidationException, CallsNotEmptyException{
        Customer c = telcoService.addCustomer("Alfonso", "15254541F", "Calle 321", "606505404");
        Long Id = c.getCustomerId();
        telcoService.updateCustomer(Id, "newname", "11111111E", "calle new");
        String expectedname = "newname";
        String expectedad = "calle new";
        if (c.getName() == expectedname) {
            assertTrue(true);
        }
        if (c.getAddress() == expectedad) {
            assertTrue(true);
        }
        telcoService.delCustomer(Id);

    }



    public void testFindCustomerByKeys() throws InstanceNotFoundException, InputValidationException, CallsNotEmptyException {
        Customer c = new Customer("Jose Gonzalez", "1020304X", "Calle 123", "666595844");
        Customer c2 = new Customer("Jose Garcia", "10203040F", "Calle 23", "662525444");
        Customer c3 = new Customer("manuel Garcia", "1223344G", "Calle 22", "622522422");
        Customer c4 = new Customer("jose Gambon", "17938448I", "Calle 13", "728582442");
        telcoService.addCustomer(c.getName(), c.getDni(), c.getAddress(), c.getPhoneNumber());
        telcoService.addCustomer(c2.getName(), c2.getDni(), c2.getAddress(), c2.getPhoneNumber());
        telcoService.addCustomer(c3.getName(), c3.getDni(), c3.getAddress(), c3.getPhoneNumber());
        telcoService.addCustomer(c4.getName(), c4.getDni(), c4.getAddress(), c4.getPhoneNumber());
        List<Customer> expected = new ArrayList<>();
        expected.add(c);
        expected.add(c2);
        List<Customer> expected2 = new ArrayList<>();
        expected2.add(c2);
        expected2.add(c4);
        List<Customer> expected3 = new ArrayList<>();
        expected3.add(c);
        expected3.add(c2);
        expected3.add(c4);
        List<Customer> expected4 = new ArrayList<>();
        List<Customer> lista = telcoService.findClientByKeywords("jose g", 0, 2);
        List<Customer> lista2 = telcoService.findClientByKeywords("jose g", 1, 2);
        List<Customer> lista3 = telcoService.findClientByKeywords("jose g", null, null);
        List<Customer> lista4 = telcoService.findClientByKeywords("jose g", -1, -1);
        assertEquals(expected, lista);
        assertEquals(expected2, lista2);
        assertEquals(expected3, lista3);
        assertEquals(expected4, lista4);
        telcoService.delCustomer(c.getCustomerId());
        telcoService.delCustomer(c2.getCustomerId());
        telcoService.delCustomer(c3.getCustomerId());
        telcoService.delCustomer(c4.getCustomerId());
        c = null;
        c2 = null;
        c3 = null;
        c4 = null;
    }

    @Test
    public void testAddFindPendingCalls() throws InputValidationException, InvalidCallStateException, InstanceNotFoundException,
            MonthNotDoneException {
        Customer c = new Customer("Juan Manuel", "12456789G", "Calle falsa 343", "666000444");
        Customer c1 = telcoService.addCustomer(c.getName(), c.getDni(), c.getAddress(), c.getPhoneNumber());
        LocalDateTime time1 = LocalDateTime.of(2021, 10, 12, 00, 00); // 12-10-2021
        LocalDateTime time2 = LocalDateTime.of(2021, 10, 6, 00, 00); // 6-10-2021
        LocalDateTime time3 = LocalDateTime.of(2021, 9, 9, 00, 00); // 9-9-2021
        LocalDateTime time4 = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), 1, 00, 00); // 2-7-2022
        PhoneCall call1 = new PhoneCall(c1.getCustomerId(), time1, (long) 1000,
                "696787434", PhoneCallType.NATIONAL);
        PhoneCall call2 = new PhoneCall(c1.getCustomerId(), time2, (long) 300,
                "606707404", PhoneCallType.NATIONAL);
        PhoneCall call3 = new PhoneCall(c1.getCustomerId(), time3, (long) 500,
                "606707404", PhoneCallType.NATIONAL);
        PhoneCall call4 = new PhoneCall(c1.getCustomerId(), time3, (long) 200,
                "606707405", PhoneCallType.LOCAL);
        PhoneCall pcall1 = telcoService.addPhoneCall(call1.getCustomerId(), call1.getStartDate(), call1.getDuration(),
                call1.getDestinationNumber(), call1.getPhoneCallType());
        PhoneCall pcall2 = telcoService.addPhoneCall(call2.getCustomerId(), call2.getStartDate(), call2.getDuration(),
                call2.getDestinationNumber(), call2.getPhoneCallType());
        PhoneCall pcall3 = telcoService.addPhoneCall(call3.getCustomerId(), call3.getStartDate(), call3.getDuration(),
                call3.getDestinationNumber(), call3.getPhoneCallType());
        PhoneCall pcall4 = telcoService.addPhoneCall(call4.getCustomerId(), call4.getStartDate(), call4.getDuration(),
                call4.getDestinationNumber(), call4.getPhoneCallType());

        List<PhoneCall> lista = telcoService.findPhoneCallPending(c1.getCustomerId(), 2021, 10);
        List<PhoneCall> expected = new ArrayList<>();
        expected.add(pcall1);
        expected.add(pcall2);
        assertEquals(expected, lista); // Test Funcion
        telcoService.UpdatePhoneState(c1.getCustomerId(), 2021, 10);
        assertThrows(InvalidCallStateException.class, () ->
                    { telcoService.findPhoneCallPending(c1.getCustomerId(), 2021, 10); }); // Test Excepcion InvalidCallStateException
        assertThrows(MonthNotDoneException.class, () ->
                    { telcoService.findPhoneCallPending(c1.getCustomerId(), LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()); }); // Test Excepcion MonthNotDoneException
        c = null;
        call1 = null;
        call2 = null;
        call3 = null;
        call4 = null;
        pcall1 = null;
        pcall2 = null;
        pcall3 = null;
        pcall4 = null;
    }

    @Test
    public void testUpdatePhoneCall() throws InputValidationException, InstanceNotFoundException,MonthNotDoneException, CallsNotEmptyException {
        Customer c = new Customer("Ana Maria", "18283848E", "Calle falsa 262", "666588884");
        Customer c1 = telcoService.addCustomer(c.getName(), c.getDni(), c.getAddress(), c.getPhoneNumber());
        LocalDateTime mes = LocalDateTime.of(2021, 9, 1, 0, 0);
        LocalDateTime finmes = mes.plusMonths(1);
        LocalDateTime time = LocalDateTime.of(2021, 9, 24, 10, 0);
        LocalDateTime time2 = LocalDateTime.of(2021, 10, 1, 17, 0);
        List<PhoneCall> calls;
        PhoneCallStatus expected = PhoneCallStatus.BILLED;
        PhoneCallStatus expected2 = PhoneCallStatus.PAID;
        telcoService.addPhoneCall(c1.getCustomerId(), time, (long) 1000,
                "699777444", PhoneCallType.NATIONAL);
        telcoService.addPhoneCall(c1.getCustomerId(), time2, (long) 1000,
                "699777444", PhoneCallType.NATIONAL);
        telcoService.UpdatePhoneState(c1.getCustomerId(), 2021, 9);
        calls = telcoService.findPhoneCallInterval(c1.getCustomerId(), mes, finmes, null, null, null);
        for (PhoneCall call : calls) {
            if (call.getPhoneCallStatus() != expected) {
                assertTrue(false);
            }
        }
        telcoService.UpdatePhoneState(c1.getCustomerId(), 2021, 9);
        for (PhoneCall call : calls) {
            if (call.getPhoneCallStatus() != expected2) {
                assertTrue(false);
            }
        }
        calls = telcoService.findPhoneCallInterval(c1.getCustomerId(), finmes,finmes.plusMonths(1), null, null, null);
        System.out.println(calls);
        if (calls.size() == 0){assertTrue(false);}
        deleteCalls();
        telcoService.delCustomer(c1.getCustomerId());
        deleteCalls();
    }

    @Test
    public void testFindCallInterval() throws InputValidationException, InstanceNotFoundException, CallsNotEmptyException {
        Customer c1bis = new Customer("Sol Maria", "19899870K", "Calle falsa 268", "666599994");
        Customer c2bis = new Customer("Juan Ramon", "10894560I", "Calle falsa 124", "974578392");
        Customer c1 = telcoService.addCustomer(c1bis.getName(), c1bis.getDni(), c1bis.getAddress(), c1bis.getPhoneNumber());
        Customer c2 = telcoService.addCustomer(c2bis.getName(), c2bis.getDni(), c2bis.getAddress(), c2bis.getPhoneNumber());
        LocalDateTime time = LocalDateTime.of(2022, 1, 4, 10, 0);
        LocalDateTime time1 = LocalDateTime.of(2022, 1, 22, 19, 32);
        LocalDateTime time2 = LocalDateTime.of(2022, 2, 3, 17, 10);
        LocalDateTime time3 = LocalDateTime.of(2022, 2, 6, 22, 52);
        LocalDateTime time4 = LocalDateTime.of(2022, 6, 1, 17, 0);
        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 4, 1, 0, 0);
        PhoneCall call;
        List<PhoneCall> expected = new ArrayList<>();
        List<PhoneCall> expected2 = new ArrayList<>();
        List<PhoneCall> expected3 = new ArrayList<>();
        List<PhoneCall> expected4 = new ArrayList<>();
        call = telcoService.addPhoneCall(c1.getCustomerId(), time, (long) 1000,
                "699000888", PhoneCallType.NATIONAL);
        expected.add(call);

        call = telcoService.addPhoneCall(c1.getCustomerId(), time1, (long) 140,
                "699000888", PhoneCallType.NATIONAL);
        expected.add(call);
        expected4.add(call);
        call = telcoService.addPhoneCall(c1.getCustomerId(), time2, (long) 23,
                "91699000888", PhoneCallType.INTERNATIONAL);
        expected.add(call);
        expected3.add(call);
        expected4.add(call);
        telcoService.addPhoneCall(c2.getCustomerId(), time3, (long) 200,
                "91699000888", PhoneCallType.INTERNATIONAL);
        telcoService.addPhoneCall(c1.getCustomerId(), time4, (long) 200,
                "91699000888", PhoneCallType.INTERNATIONAL);
        List<PhoneCall> lista = telcoService.findPhoneCallInterval(c1.getCustomerId(), start, end, null,
                null, null);
        List<PhoneCall> lista2 = telcoService.findPhoneCallInterval(c1.getCustomerId(), end, start, null,
                null, null);
        List<PhoneCall> lista3 = telcoService.findPhoneCallInterval(c1.getCustomerId(), start, end,
                PhoneCallType.INTERNATIONAL, null, null);
        List<PhoneCall> lista4 = telcoService.findPhoneCallInterval(c1.getCustomerId(), start, end,
                null, 1, null);
        assertEquals(expected, lista);
        assertEquals(expected2, lista2);
        assertEquals(expected3, lista3);
        assertEquals(expected4, lista4);
        deleteCalls();
        telcoService.delCustomer(c1.getCustomerId());
        telcoService.delCustomer(c2.getCustomerId());
        deleteCalls();
    }
}