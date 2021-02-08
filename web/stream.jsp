<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/displaytag.css" />
    <title>Echoing HTML Request Parameters</title>
</head>
<body background="resources/image4.png" >
    <form style="margin-left: 10%" action="index.jsp" method="POST">
        <button type="submit">Back</button>
    </form>
    <center>
        <h1>Ontology Search - IoT Stream</h1> <br>
        <%--<img src="resources/smoke2.png" style="width: 500px" />--%>
    </center>           
    <center><h2>Stream Information</h2></center>
    <form style="margin-left: 10%; margin-right: 10%" action="StreamSearch" method="POST">
        <div class="form-group">
            <label for="Sensor">Sensor: </label>
            <select class="form-control" name="sensors" required="true">
                <option value=""> </option>
                <c:forEach var="sen" items="${sensors}">   
                    <option value="${sen}">${sen}</option>                 
                </c:forEach>
            </select><br><br/>
            
            <label for="Service">Service: </label>
            <select class="form-control" name="services" required="true">
                <option value=""> </option>
                <c:forEach var="ser" items="${services}">   
                    <option value="${ser}">${ser}</option>                 
                </c:forEach>
            </select><br><br/>
            
            <label for="Location">Location: </label>
            <select class="form-control" name="locations" required="true">
                <option value=""> </option>
                <c:forEach var="l" items="${locations}">   
                    <option value="${l}">${l}</option>                 
                </c:forEach>
            </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Stream Data" /><br><br/>
            
            <label for="StreamData">Stream Data: </label>
            <c:forEach items="${streamData}" var="sd">
                <c:forEach items="${sd}" var="variable">
                     <li>${variable}</li>
                </c:forEach>
            </c:forEach> 
        </div>
    </form>
    
    <center><h2>Stream's Sensors</h2></center>    
        <div class="form-group">         
                 
        <table class="styled-table">
           <thead>
           <tr>
               <th>Resource</th>
               <th>Sensor</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${streamSensors}" var="row">
                   <tr>
               <c:forEach items="${row}" var="ss">
                   <td>${ss}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>
    
    <center><h2>Stream's Observations</h2></center>    
        <div class="form-group">         
                 
        <table class="styled-table">
           <thead>
           <tr>
               <th>Resource</th>
               <th>Observation</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${streamObservations}" var="row">
                   <tr>
               <c:forEach items="${row}" var="sob">
                   <td>${sob}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>
    
    
    
     
     <center><h2>Data properties</h2></center>
     <form style="margin-left: 10%; margin-right: 10%" action="StreamSearch" method="POST">
        <div class="form-group">         
         <label for="Data properties">Data properties: </label>
            <select class="form-control" name="dp" required="true">
                <option value=""> </option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#CO">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#CO</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#H">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#H</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#NO2">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#NO2</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#O3">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#O3</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#P">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#P</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PM10">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PM10</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PM25">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#PM25</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#SO2">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#SO2</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#WG">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#WG</option> 
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Humidity">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Humidity</option>
                <option value="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Temperature">http://purl.oclc.org/NET/UNIS/fiware/iot-lite#Temperature</option>
        </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Stream Data properties" /><br><br/>
         
        <table class="styled-table">
           <thead>
           <tr>
               <th>Object Property</th>
               <th>Individual</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${streamDP}" var="row">
                   <tr>
               <c:forEach items="${row}" var="v">
                   <td>${v}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>
         
         <center><h2>AQI>100</h2></center>
     
        <div class="form-group">         
                 
        <table class="styled-table">
           <thead>
           <tr>
               <th>Object Property</th>
               <th>Individual</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${streamPM25}" var="row">
                   <tr>
               <c:forEach items="${row}" var="va">
                   <td>${va}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>

</body>
</html>
