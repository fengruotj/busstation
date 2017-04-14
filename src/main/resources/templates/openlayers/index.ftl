<!DOCTYPE html>
<html>
<head>
    <title>Accessible Map</title>
    <link rel="stylesheet" href="http://openlayers.org/en/v3.15.1/css/ol.css" type="text/css">
    <script src="http://openlayers.org/en/v3.15.1/build/ol.js"></script>

    <#--<link rel="stylesheet" href="/js/openlayers/ol.css" type="text/css">-->
    <#--<script src="/js/openlayers/ol.js"></script>-->
    <style>
        a.skiplink {
            position: absolute;
            clip: rect(1px, 1px, 1px, 1px);
            padding: 0;
            border: 0;
            height: 1px;
            width: 1px;
            overflow: hidden;
        }
        a.skiplink:focus {
            clip: auto;
            height: auto;
            width: auto;
            background-color: #fff;
            padding: 0.3em;
        }
        #map:focus {
            outline: #4A74A8 solid 0.15em;
        }
    </style>
</head>
<body>
<a class="skiplink" href="#map">Go to map</a>
<div id="map" class="map" tabindex="0"></div>
<button id="zoom-out">Zoom out</button>
<button id="zoom-in">Zoom in</button>
<script>

//    var projExtent = ol.proj.get('EPSG:5921').getExtent();
//    var startResolution = ol.extent.getWidth(projExtent) / 256;
//    var resolutions = new Array(22);
//    for (var i = 0, ii = resolutions.length; i < ii; ++i) {
//        resolutions[i] = startResolution / Math.pow(2, i);
//    }
//
    //定义图层的宽度和高度  经纬度范围
//    var tileGrid = new ol.tilegrid.TileGrid({
//        extent: [114.231033,30.505859,114.459162,30.618072],
//        tileSize: [768, 377]
//    });


//    var projection = new ol.proj.Projection({
//        code: 'EPSG:5921',
//        // The extent is used to determine zoom level 0. Recommended values for a
//        // projection's validity extent can be found at http://epsg.io/.
//        extent: [114.231033,30.505859,114.459162,30.618072],
//        units: 'm'
//    });
//    ol.proj.addProjection(projection);

    //定义道路路线图层
    var wmsLayer = new ol.layer.Image({
        source: new ol.source.ImageWMS({
            extent: [420000, 30000, 900000, 350000],
            url: 'http://localhost:8080/OL4JSFProxy/wms',
            params: {
                'LAYERS': 'cite:roadline',
                'TILED': true,
                'srs':'EPSG:5921',
            },
            serverType: 'geoserver',
//            tileGrid: tileGrid
        })
    });

    var istanbul = ol.proj.fromLonLat([114.231033,30.505859]);

    //定义地图元素
    var map = new ol.Map({
        layers: [
//            new ol.layer.Tile({
//                source: new ol.source.OSM()
//            }),
           wmsLayer
        ],
        target: 'map',
        controls: ol.control.defaults({
            attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
                collapsible: false
            })
        }),
        view: new ol.View({
            center: istanbul,
            zoom: 12
        })
    });

    document.getElementById('zoom-out').onclick = function() {
        var view = map.getView();
        var zoom = view.getZoom();
        view.setZoom(zoom - 1);
    };

    document.getElementById('zoom-in').onclick = function() {
        var view = map.getView();
        var zoom = view.getZoom();
        view.setZoom(zoom + 1);
    };
</script>
</body>
</html>
