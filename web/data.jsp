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
        <h1>Ontology Search - General </h1> <br>
        <%--<img src="resources/smoke2.png" style="width: 500px" />--%>
    </center>   
    
             
    <form style="margin-left: 10%; margin-right: 10%" action="OntologySearch" method="POST">         
        <div class="form-group">            
            <label for="Services">Services: </label>
            <select class="form-control" name="services" required="true">
                <option value=""> </option>
                <c:forEach var="s" items="${services}">   
                    <option value="${s}">${s}</option>                 
                </c:forEach>
            </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Devices and Streams by Service" /><br><br/>
            
            <label for="DeviceByService">Devices by Service: </label>
            <c:forEach items="${deviceByService}" var="f">
                <c:forEach items="${f}" var="variable">
                     <li>${variable}</li>
                </c:forEach>
            </c:forEach><br></br>
                     
                       
            <label for="StreamByService">Stream by Service: </label>
            <c:forEach items="${streamByService}" var="bb">
                <c:forEach items="${bb}" var="variable">
                     <li>${variable}</li>
                </c:forEach>
            </c:forEach>
                    
        </div>
    </form>

     
     <center><h2>Data properties</h2></center>
     <form style="margin-left: 10%; margin-right: 10%" action="OntologySearch" method="POST">
        <div class="form-group">         
         <label for="Data properties">Data properties: </label>
            <select class="form-control" name="dp" required="true">
                <option value=""> </option>
                <c:forEach var="dp" items="${dp}">   
                    <option value="${dp}">${dp}</option>                 
                </c:forEach>
        </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Stream Data properties" /><br><br/>
         
        <table class="styled-table">
           <thead>
           <tr>
               <th>Individual</th>
               <th>Value</th>
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
     </form>
         
        <center><h2>Object properties</h2></center>
     <form style="margin-left: 10%; margin-right: 10%" action="OntologySearch" method="POST">
        <div class="form-group">         
         <label for="Object properties">Object properties: </label>
            <select class="form-control" name="op" required="true">
                <option value=""> </option>
                <c:forEach var="op" items="${op}">   
                    <option value="${op}">${op}</option>                 
                </c:forEach>
        </select><input class="example_b" style="padding: 7px" type="submit" name="submit" value="Show Stream Object properties" /><br><br/>
         
        <table class="styled-table">
           <thead>
           <tr>
               <th>Individual</th>
               <th>Value</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${streamOP}" var="row">
                   <tr>
               <c:forEach items="${row}" var="vOP">
                   <td>${vOP}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>
     </form>
         
         <center><h2>Times properties were used</h2></center>        
        <table class="styled-table">
           <thead>
           <tr>
               <th>Properties</th>
               <th>Times used</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${timesPropertyUsed}" var="row">
                   <tr>
               <c:forEach items="${row}" var="tpu">
                   <td>${tpu}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>
         
        <center><h2>Ontology Facts</h2></center>    
        <div class="form-group">         
                 
        <table class="styled-table">
           <thead>
           <tr>
               <th>Object Property</th>
               <th>Individual</th>
           </tr>
           </thead>
           <tbody>
               <c:forEach items="${ontologyFacts}" var="row">
                   <tr>
               <c:forEach items="${row}" var="ct">
                   <td>${ct}</td>
               </c:forEach>
                   </tr>
               </c:forEach> 
           </tbody>
        </table>
        </div>

</body>
</html>
