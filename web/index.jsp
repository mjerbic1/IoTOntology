<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/displaytag.css" />
    <title>Echoing HTML Request Parameters</title>
</head>
<body background="resources/index.png">
<center>
    <br>
    
    <img src="resources/naslov.png" style="width: 250px" />
    <form>
        
    </form>
    <div class="button_cont" align="center">
        <form style="margin-left: 10%; margin-right: 10%" action="StandardSearch" method="POST">
            <button type="submit" class="example_b">Location Based Search</button>
        </form>
    </div>
    <br><br>
    
    <form style="margin-left: 10%; margin-right: 10%" action="StationSearch" method="POST">
        <button type="submit" class="example_b">Closest Station Based Search</button>
    </form>
    
    <br><br>
<%--
    <form style="margin-left: 10%; margin-right: 10%" action="ForecastSearch" method="POST">
        <button type="submit" class="example_b">Check Forecast</button>
    </form>

    <br><br>
 --%>   
    <form style="margin-left: 10%; margin-right: 10%" action="OntologySearch" method="POST">
        <button type="submit" class="example_b">Show ontology data</button>
    </form>

    <br><br>
    
    <form style="margin-left: 10%; margin-right: 10%" action="SensorSearch" method="POST">
        <button type="submit" class="example_b">Show Sensors</button>
    </form>
    
    <br><br>
    
    <form style="margin-left: 10%; margin-right: 10%" action="StreamSearch" method="POST">
        <button type="submit" class="example_b">Show IoT Streams</button>
    </form>
    
    <br><br>
    
    <form style="margin-left: 10%; margin-right: 10%" action="ObservationSearch" method="POST">
        <button type="submit" class="example_b">Show Observation Streams</button>
    </form>
</center>

</body>
</html>