var map;
var marker;
var infowindow;

var directionsDisplay; // Instanciaremos ele mais tarde, que será o nosso google.maps.DirectionsRenderer
var directionsService = new google.maps.DirectionsService();


function initialize() {
    directionsDisplay = new google.maps.DirectionsRenderer(); // Instanciando...

    var latlng = new google.maps.LatLng(-29.896069, -51.192037);

    var options = {
        zoom: 10,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("mapa"), options);
    directionsDisplay.setMap(map); // Relacionamos o directionsDisplay com o mapa desejado
    infoWindow = new google.maps.InfoWindow();

    google.maps.event.addListener(map, 'click', function () {
        infoWindow.close();

    });

    carregarPontosTaxiInfo();
    carregarPontosCasaInfo();

    //rota
    $('#formRota').submit(function (event) {
        event.preventDefault();

        var enderecoPartida = $("#txtEnderecoPartida").val();
        var enderecoChegada = $("#txtEnderecoChegada").val();

        var request = {// Novo objeto google.maps.DirectionsRequest, contendo:
            origin: enderecoPartida, // origem
            destination: enderecoChegada, // destino
            travelMode: google.maps.TravelMode.DRIVING // meio de transporte, nesse caso, de carro
        };

        directionsService.route(request, function (result, status) {
            if (status == google.maps.DirectionsStatus.OK) { // Se deu tudo certo
                directionsDisplay.setDirections(result); // Renderizamos no mapa o resultado
            }
        });
    });
}
google.maps.event.addDomListener(window, 'load', initialize);



function carregarPontosTaxiInfo() {

    $.getJSON("resources/mapataxi/pontosTaxiInfo.json", function (pontos) {

        $.each(pontos, function (TaxiDog, ponto) {

            var latlng = new google.maps.LatLng(ponto.Latitude, ponto.Longitude);
            var nome = ponto.Nome;
            var fone = ponto.Fone;
            var local = ponto.Local;
            var email = ponto.Email;

            createMarkerTaxi(latlng, nome, fone, local, email);

        });
    });
}

function createMarkerTaxi(latlng, nome, fone, local, email) {
    var marker = new google.maps.Marker({
        map: map,
        position: latlng,
        title: nome,
        icon: "resources/mapataxi/taxi.png"
    });

    google.maps.event.addListener(marker, 'click', function () {

        var iwContent = '<div id="iw_container">' +
                '<div class="iw_title">' + nome + '</div>'
                + '<div class="iw_title">' + fone + '</div>'
                + '<div class="iw_title">' + local + '</div>'
                + '<div class="iw_title">' + email + '</div>'

                + '</div>';

        infoWindow.setContent(iwContent);
        infoWindow.open(map, marker);
    });
}

function carregarPontosCasaInfo() {

    $.getJSON("resources/mapataxi/pontosCasaInfo.json", function (pontos) {

        $.each(pontos, function (TaxiDog, ponto) {

            var latlng = new google.maps.LatLng(ponto.Latitude, ponto.Longitude);
            var nome = ponto.Nome;
            var fone = ponto.Fone;
            var local = ponto.Local;
            createMarkerCasa(latlng, nome, fone, local);

        });
    });
}

function createMarkerCasa(latlng, nome, fone, local) {
    var marker = new google.maps.Marker({
        map: map,
        position: latlng,
        title: nome,
        icon: "resources/mapataxi/casapeq.png"
    });

    google.maps.event.addListener(marker, 'click', function () {

        var iwContent = '<div id="iw_container">' +
                '<div class="iw_title">' + nome + '</div>'
                + '<div class="iw_title">' + fone + '</div>'
                + '<div class="iw_title">' + local + '</div>'
                + '</div>';

        infoWindow.setContent(iwContent);
        infoWindow.open(map, marker);
    });
}