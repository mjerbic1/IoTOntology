<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Echoing HTML Request Parameters</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
    </head>
<body background="resources/image4.png">
    <form style="margin-left: 10%" action="index.jsp" method="POST">
        <button type="submit">Back</button>
    </form>  
<center>
    <br>
    <h1>Air Quality data search based on location</h1> <br>    
    
    <form method="post">
        <input type="hidden" name="submitted" value="true" />
            <p>
            Enter location:            
            <input type="text" name="insert" required="true"/>        
        <input class="example_b" style="padding: 7px" type="submit" value="Insert location" />
    </form>
    
    Enter coordinates: 
    <form method="post">
        <input type="hidden" name="submitted" value="true" />                        
            <input type="text" name="insertLat" required="true"/>
            <input type="text" name="insertLong" required="true"/>
        <input class="example_b" style="padding: 7px" type="submit" value="Insert coordinates" />
    </form>
    
        
            <table class="styled-table">               
                <tbody>
                    
                    <tr>
                        <td>Location: </td>
                        <td style="color: black;"><c:out value="${requestScope.valueLoc}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Latitude: </td>
                        <td style="color: black;"><c:out value="${requestScope.valueLat}"></c:out></td>
                    </tr>
                    <tr>
                        <td>Longitude: </td>
                        <td style="color: black;"><c:out value="${requestScope.valueLon}"></c:out></td>
                    </tr>
                    
                    <tr>                     
                        <td>Temperature: </td>
                        <td style="color: black;"><c:out value="${requestScope.valueTemperature}"></c:out></td>
                    </tr>
                    <tr>   
                        <td>Humidity: </td>
                        <td style="color: black;"><c:out value="${requestScope.valueHumidity}"></c:out></td>
                    </tr>
                    <tr> 
                        <td>Pressure: </td>
                        <td style="color: black;"><c:out value="${requestScope.valuePressure}"></c:out></td>
                    </tr>
                    
                    <tr>
                        <td>Carbon Oxide (CO): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueco}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Hydrogen (H): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueh}"></c:out></td>
                    </tr>
                    <tr>
                        <td>Nitrogen Dioxide (NO2): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueno2}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Particulate Matter (PM10): </td>
                        <td style="color: black;"><c:out value="${requestScope.valuepm10}"></c:out></td>
                    </tr>
                    <tr>
                        <td>Particulate Matter (PM25): </td>
                        <td style="color: black;"><c:out value="${requestScope.valuepm25}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Sulfur Dioxide (SO2): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueso2}"></c:out></td>
                    </tr>
                    <tr>
                        <td>t: </td>
                        <td style="color: black;"><c:out value="${requestScope.valuet}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>w: </td>
                        <td style="color: black;"><c:out value="${requestScope.valuew}"></c:out></td>
                    </tr>
                    <tr>
                        <td>wg: </td>
                        <td style="color: black;"><c:out value="${requestScope.valuewg}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Ozone (O3): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueo3}"></c:out></td>
                    </tr>
                    <tr>
                        <td>Ultra Violet Index (UVI): </td>
                       <td style="color: black;"><c:out value="${requestScope.valueuvi}"></c:out></td>
                    </tr>                  
                </tbody>              
            </table>
   
                 
        Pollutants information: 
        NO2:  Nitrogen Dioxide <c:out value="${requestScope.valueno2desc}"></span></c:out><br></br>
        O3:  Ozone <c:out value="${requestScope.valueo3desc}"></span></c:out><br></br>
        PM10:  Particulate Matter PM10 <c:out value="${requestScope.valuepm10desc}"></span></c:out><br></br>
        PM25:  Particulate Matter PM25 <c:out value="${requestScope.valuepm25desc}"></span></c:out><br></br>

</body>
</html>
