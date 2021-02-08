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
    <h1>Air Quality Nearest Station</h1><br>
        
        <table class="styled-table">               
                <tbody>                                     
                    <tr>
                        <td>Carbon Oxide (CO): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueco}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Hydrogen (H): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueh2}"></c:out></td>
                    </tr>
                    <tr>
                        <td>Nitrogen Dioxide (NO2): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueno22}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Particulate Matter (PM10): </td>
                        <td style="color: black;"><c:out value="${requestScope.valuepm102}"></c:out></td>
                    </tr>
                    <tr>
                        <td>Particulate Matter (PM25): </td>
                        <td style="color: black;"><c:out value="${requestScope.valuepm252}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Sulfur Dioxide (SO2): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueso22}"></c:out></td>
                    </tr>
                    <tr>
                        <td>t: </td>
                        <td style="color: black;"><c:out value="${requestScope.valuet2}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>w: </td>
                        <td style="color: black;"><c:out value="${requestScope.valuew2}"></c:out></td>
                    </tr>
                    <tr>
                        <td>wg: </td>
                        <td style="color: black;"><c:out value="${requestScope.valuewg2}"></c:out></td>
                    </tr>
                    <tr>     
                        <td>Ozone (O3): </td>
                        <td style="color: black;"><c:out value="${requestScope.valueo32}"></c:out></td>
                    </tr>                                   
                </tbody>              
            </table>
    

        <label>Pollutants information: </label><br></br>
        NO2:  Nitrogen Dioxide <c:out value="${requestScope.valueno2desc}"></span></c:out><br></br>
        O3:  Ozone <c:out value="${requestScope.valueo3desc}"></span></c:out><br></br>
        PM10:  Particulate Matter PM10 <c:out value="${requestScope.valuepm10desc}"></span></c:out><br></br>
        PM25:  Particulate Matter PM25 <c:out value="${requestScope.valuepm25desc}"></span></c:out><br></br>

</body>
</html>
