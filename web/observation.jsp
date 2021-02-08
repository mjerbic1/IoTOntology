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
        <h1>Ontology Search</h1> <br>
        <%--<img src="resources/smoke2.png" style="width: 500px" />--%>
    </center>   
    
    <form style="margin-left: 10%; margin-right: 10%" action="ObservationSearch" method="POST">
        <div class="form-group">
            <label for="IoTStream">IoT Stream: </label>
            <select class="form-control" name="streams" required="true">
                <option value=""> </option>
                <c:forEach var="s" items="${streams}">   
                    <option value="${s}">${s}</option>                 
                </c:forEach>
            </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Observation by IoT Stream" /><br><br/>
            
            <label for="ObservationByStream">Observation by IoT Stream: </label>
            <c:forEach items="${observationByStream}" var="obs">
                <c:forEach items="${obs}" var="variable">
                     <li>${variable}</li>
                </c:forEach>
            </c:forEach> 
        </div>
    </form>
        
    
     <form style="margin-left: 10%; margin-right: 10%" action="ObservationSearch" method="POST">
        <div class="form-group">
            <label for="Result">Bad Result: </label>
            <select class="form-control" name="results" required="true">
                <option value=""> </option>
                <c:forEach var="ru" items="${results}">   
                    <option value="${ru}">${ru}</option>                 
                </c:forEach>
            </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Observation by Result" /><br><br/>
            
            <label for="ObservationData">Observation by Result: </label>
            <c:forEach items="${observationData}" var="od">
                <c:forEach items="${od}" var="variable">
                     <li>${variable}</li>
                </c:forEach>
            </c:forEach> 
        </div>
    </form>
     
     <center><h2>Observation facts</h2></center>
    <form style="margin-left: 10%; margin-right: 10%" action="ObservationSearch" method="POST">
        
         <div class="form-group">
        
            <label for="op">Observation object property </label>
                <select class="form-control" name="objectPropertyO" required="true">                  
                    <option value="objectPropertyO"> </option>
                    <option value="http://www.w3.org/ns/sosa#madeByStream">http://www.w3.org/ns/sosa#madeByStream</option>
                    <option value="http://www.w3.org/ns/sosa#hasResult">http://www.w3.org/ns/sosa#hasResult</option>
                </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show observation properties" /><br><br/>
        
           <table class="styled-table">
              <thead>
              <tr>
                  <th>Subject</th>
                  <th>Object</th>
              </tr>
              </thead>
              <tbody>
                  <c:forEach items="${observationFacts}" var="row">
                      <tr>
                  <c:forEach items="${row}" var="of">
                      <td>${of}</td>
                  </c:forEach>
                      </tr>
                  </c:forEach> 
              </tbody>
           </table>
        </div>
     </form>
     
     <center><h2>Data properties</h2></center>
     <form style="margin-left: 10%; margin-right: 10%" action="ObservationSearch" method="POST">
        <div class="form-group">         
         <label for="Data properties">Data properties: </label>
            <select class="form-control" name="dp" required="true">
                <option value=""> </option>
                <option value="http://www.w3.org/ns/sosa#windowEnd">http://www.w3.org/ns/sosa#windowEnd</option>
                <option value="http://www.w3.org/ns/sosa#windowStart">http://www.w3.org/ns/sosa#windowStart</option>
                <option value="http://www.w3.org/ns/sosa#resultTime">http://www.w3.org/ns/sosa#resultTime</option>
                <option value="http://www.w3.org/ns/sosa#simpleResult">http://www.w3.org/ns/sosa#simpleResult</option>
        </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Stream Data properties" /><br><br/>
         
        <table class="styled-table">
           <thead>
           <tr>
               <th>Domain</th>
               <th>Data value</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${observationDP}" var="row">
                   <tr>
               <c:forEach items="${row}" var="v">
                   <td>${v}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>
         
</body>
</html>
