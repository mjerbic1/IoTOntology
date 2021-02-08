<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Echoing HTML Request Parameters</title>
</head>
<body background="resources/image4.png">
    <form style="margin-left: 10%" action="index.jsp" method="POST">
        <button type="submit">Back</button>
    </form>
<center>
    <br>
    <h1>Forecast</h1> <br>
    
    <form method="post">
        <input type="hidden" name="submitted" value="true" />
            <p>
            Enter location:
            <input type="text" name="insert"/>
            <br />
            <c:if test="${noLoc}">
             <small><font color="red">
               Note: you must enter location
             </font></small>
            </c:if>
            </p>
        <input type="submit" value="Insert location" />
    </form>
        
        Location: <span style="white-space:pre"><c:out value="${requestScope.valueLocF}"></c:out>
        Latitude: <c:out value="${requestScope.valueLatF}"></span></c:out>
        Longitude: <c:out value="${requestScope.valueLonF}"></span></c:out>
      
  
        avg: 
        min:
        max:
        day: 
    
</center>
        

</body>
</html>
