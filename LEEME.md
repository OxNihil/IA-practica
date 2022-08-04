# Memoria justificativa del proyecto
---------------------------------------------------------------------

## Iteración 1
---------------------------------------------------------------------

### Pruebas WS-BPEL
 - URL a los documentos WSDL que es necesario utilizar:  

http://localhost:7788/TelcoServiceCAService1/TelcoService?wsdl

 - Nombre del fichero SoapUI con las peticiones: 

IA-Rest.postman_collection.json

### Justificaciones de diseño
- En la funcion updateStatus se decide no pasar como parámetro el estado, ya que el funcionamiento real es el de una máquina de estados, por lo tanto, para prevenir errores,
siempre que el estado sea PENDING se pasa a BILLED, si es BILLED se pasa a PAID, y si ya es PAID no se realizan modificaciones.

### Problemas conocidos en el diseño / implementación de la práctica
- .

### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- miembro1: Daniel Osama González : findClientByKeywords, UpdatePhoneState, findPhoneCallInterval
- miembro2: Iago Pallares Tato : findClientByDni, addPhoneCall, findPhoneCallPending
- miembro3: Sergio González López : addCustomer, delCustomer, updateCustomer

Daniel:
Consultar datos de clientes por keywords
Cambiar estado de las llamadas
Obtener datos de llamadas telefónicas de un cliente durante un intervalo de tiempo

Iago:
Consultar datos de cliente a partir de DNI/identificador
Añadir llamada telefónica
Obtener llamadas de cliente en estado pendiente en un mes que ya haya transcurrido

Sergio:
Dar de alta cliente
Eliminar cliente
Modificar cliente


## Iteración 2
---------------------------------------------------------------------

### Partes opcionales incluidas y miembros del grupo que han participado
- .

### Pruebas WS REST
- Nombre del fichero SoapUI/colección Postman con las peticiones a probar:

- Comandos maven necesarios para ejecutar las pruebas : 
pruebas.txt

### Pruebas WS-BPEL
- URL a los documentos WSDL que es necesario utilizar:

- Nombre del fichero SoapUI con las peticiones:
TelcoService-soapui-project.xml

### Justificaciones de diseño
- .

### Problemas conocidos en el diseño / implementación de la práctica
- Cuando se lanza una excepción por algún motivo, en la siguiente ejecución fallan las correlaciones y hay que volver a desplegar el servicio y hacer un undeploy en el servidor de openESB para lanzar de nuevo.

### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- Los tres miembros hemos estado al tanto de todos los tramos, centrándose Daniel más en la parte inicial del flujo, Iago en la intermedia y Sergio en la final.


