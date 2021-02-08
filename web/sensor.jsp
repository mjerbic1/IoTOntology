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
        <h1>Ontology Search - Sensors </h1> <br>
    </center>   
    <form style="margin-left: 10%; margin-right: 10%" action="SensorSearch" method="POST">
        
        <div class="form-group">
                         
            <label for="Units">Units: </label>
            <select class="form-control" name="units" display="inline" required="true"> 
                <option value=""> </option>
                <c:forEach var="u" items="${units}">                  
                    <option value="${u}">${u}</option>                 
                </c:forEach>
            </select><button class="example_b" style="padding: 7px" type="submit" name="submit">Show Sensors by Unit</button><br></br>
           
            
            <label for="SensorByUnit">Sensors by Unit: </label>
            <c:forEach items="${sensorByUnit}" var="b">
                <c:forEach items="${b}" var="variable">
                     <li>${variable}</li>
                </c:forEach>
            </c:forEach>            
                 
        </div>           
    </form>
             
    
    <form style="margin-left: 10%; margin-right: 10%" action="SensorSearch" method="POST">        
        <div class="form-group">
            
            <label for="Quantity Kind">Quantity Kind: </label>
            <select class="form-control" name="quantityKinds" required="true">
                <option value=""> </option>
                <c:forEach var="q" items="${quantityKinds}">   
                    <option value="${q}">${q}</option>                 
                </c:forEach>
            </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Sensors by Quantity Kind" /><br><br/>
            
            <label for="SensorByQuantityKind">Sensors by Quantity Kind: </label>
            <c:forEach items="${sensorByQuantityKind}" var="sbq">
                <c:forEach items="${sbq}" var="variable">
                     <li>${variable}</li>
                </c:forEach>
            </c:forEach>  

        </div>       
    </form>
    
     <center><h2>Sensor Information</h2></center>
     <form style="margin-left: 10%; margin-right: 10%" action="SensorSearch" method="POST"> 
        <div class="form-group">
            <label for="Sensor2">Sensor: </label>
                <select class="form-control" name="sensors2" required="true">
                    <option value=""> </option>
                    <c:forEach var="sss" items="${sensors2}">   
                        <option value="${sss}">${sss}</option>                 
                    </c:forEach>
                </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show sensor data" /><br><br/>

           <table class="styled-table">
              <thead>
              <tr>
                  <th>Object Property</th>
                  <th>Individual</th>
              </tr>
              </thead>
              <tbody>
                  <c:forEach items="${sensorInfo}" var="row">
                      <tr>
                  <c:forEach items="${row}" var="v">
                      <td>${v}</td>
                  </c:forEach>
                      </tr>
                  </c:forEach> 
              </tbody>
           </table>
        </div>
     </form>
     
        
        <center><h2>Sensor's Quantity Kinds</h2></center>    
        <div class="form-group">         
                 
        <table class="styled-table">
           <thead>
           <tr>
               <th>Resource</th>
               <th>Quantity Kind</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${sensorQuantityKinds}" var="row">
                   <tr>
               <c:forEach items="${row}" var="sqk">
                   <td>${sqk}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>
        
        <center><h2>Sensor's Devices</h2></center>    
        <div class="form-group">         
                 
        <table class="styled-table">
           <thead>
           <tr>
               <th>Resource</th>
               <th>Device</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${sensorDevices}" var="row">
                   <tr>
               <c:forEach items="${row}" var="sdd">
                   <td>${sdd}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>

</body>
</html>
