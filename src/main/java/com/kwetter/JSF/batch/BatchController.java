package com.kwetter.JSF.batch;

import com.kwetter.dao.TweetDao;
import com.kwetter.dao.jpa.JPA;
import com.kwetter.model.Tweet;
import org.eclipse.persistence.queries.CursoredStream;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polygon;

import javax.annotation.PostConstruct;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hein on 5/25/17.
 */
@Named
@RequestScoped
public class BatchController implements Serializable {

    @Inject @JPA
    private TweetDao tweetDao;

    private long execID;
    private JobOperator jobOperator;

    private MapModel polygonModel;
    String germanBorderCoordinates = "13.9554144313245,53.72361034704952,9.999999999999998 14.20839295090622,53.97235245945657,9.999999999999998 13.67671947614205,54.24858922837771,9.999999999999998 13.65745923424034,54.454507039071,9.999999999999998 13.38880622464598,54.65940285386881,9.999999999999998 12.93955410097746,54.43372518162033,9.999999999999998 12.49522627495801,54.45798533121155,9.999999999999998 11.98508923201705,54.15702910998268,9.999999999999998 11.38582387998969,53.91295854820655,9.999999999999998 10.87041800170717,54.03362597460777,9.999999999999998 11.15203349866248,54.22985419541703,9.999999999999998 11.2904137642329,54.40886078227636,9.999999999999998 11.0625018659324,54.53680231507768,9.999999999999998 10.7307397942779,54.32343190801309,9.999999999999998 10.00385018523733,54.51144009948558,9.999999999999998 9.796093222740936,54.8910830791033,9.999999999999998 8.724860081387234,54.85828787119236,9.999999999999998 8.661616447715716,54.76682612233567,9.999999999999998 8.550020752160791,54.28938041047162,9.999999999999998 8.819483394936654,54.29868459870834,9.999999999999998 8.887722988471154,54.13930612720998,9.999999999999998 8.91693083328768,53.8914197489343,9.999999999999998 8.559534328050276,53.83513936453196,9.999999999999998 8.468713678819043,53.59864111658062,9.999999999999998 8.251808629232016,53.41609853169224,9.999999999999998 8.096218443259716,53.67185601132858,9.999999999999998 7.261522780542606,53.72482190378862,9.999999999999998 7.030062654582878,53.36422139370038,9.999999999999998 7.216877170200775,53.2564921559505,9.999999999999998 6.877704248586909,51.97179585207389,9.999999999999998 6.054147963535328,51.85509987985863,9.999999999999998 5.990072810790395,50.74907251386956,9.999999999999998 6.293162114610567,50.4254062109983,9.999999999999998 6.115258373692413,50.11462255988881,9.999999999999998 6.415619276446975,49.7628664122294,9.999999999999998 6.414199557349363,49.46308096123954,9.999999999999998 8.248813485383849,49.0118693137962,9.999999999999998 7.559276610212023,47.77145104828055,9.999999999999998 7.749703165143317,47.58055756065765,9.999999999999998 8.712129690315578,47.74726412480993,9.999999999999998 9.720908737813232,47.55982159551824,9.999999999999998 10.18435363516244,47.22949614918642,9.999999999999998 10.50038254170158,47.55664472892166,9.999999999999998 11.16045873834514,47.40016288287761,9.999999999999998 12.32954728649809,47.63341922421113,9.999999999999998 13.01044728047616,47.43460104053264,9.999999999999998 12.80356786678746,48.12393333960909,9.999999999999998 13.54596774659309,48.5667138655369,9.999999999999998 13.74029544157428,48.52307477154136,9.999999999999998 13.86885519995614,48.73620319386774,9.999999999999998 12.46220840545955,49.74096549099375,9.999999999999998 12.4799819379195,49.91068221042917,9.999999999999998 12.13856288516257,50.30246225649023,9.999999999999998 12.31860838182574,50.19156673012799,9.999999999999998 14.32952789559532,50.96009399381583,9.999999999999998 14.73547086851441,50.84382146983565,9.999999999999998 14.77966075250614,51.51102137648616,9.999999999999998 14.73396642658119,52.15277914579797,9.999999999999998 14.61872983657668,52.57029676642026,9.999999999999998 14.10490565035165,52.87837798405468,9.999999999999998 14.495511258793,53.21004985322417,9.999999999999998 14.24218368867247,53.70974553218998,9.999999999999998 13.9554144313245,53.72361034704952,9.999999999999998";
    String berlinPolygon = "13.730960,52.394550,0\n" +
            "13.716440,52.396500,0\n" +
            "13.699400,52.387190,0\n" +
            "13.706830,52.377930,0\n" +
            "13.695380,52.364140,0\n" +
            "13.679290,52.364900,0\n" +
            "13.650200,52.334460,0\n" +
            "13.635350,52.338250,0\n" +
            "13.629780,52.347130,0\n" +
            "13.637820,52.367730,0\n" +
            "13.626060,52.376420,0\n" +
            "13.603780,52.369050,0\n" +
            "13.587380,52.388130,0\n" +
            "13.564480,52.385300,0\n" +
            "13.530440,52.386050,0\n" +
            "13.529820,52.394360,0\n" +
            "13.517760,52.398520,0\n" +
            "13.478920,52.392100,0\n" +
            "13.462830,52.417580,0\n" +
            "13.422290,52.407960,0\n" +
            "13.433430,52.385300,0\n" +
            "13.421980,52.373400,0\n" +
            "13.383920,52.375480,0\n" +
            "13.382680,52.385300,0\n" +
            "13.367210,52.386050,0\n" +
            "13.365660,52.392660,0\n" +
            "13.337500,52.405600,0\n" +
            "13.310890,52.395020,0\n" +
            "13.293250,52.409560,0\n" +
            "13.270660,52.400690,0\n" +
            "13.245600,52.402390,0\n" +
            "13.239410,52.417490,0\n" +
            "13.174120,52.404650,0\n" +
            "13.173500,52.391250,0\n" +
            "13.158330,52.387280,0\n" +
            "13.128320,52.384070,0\n" +
            "13.084690,52.408240,0\n" +
            "13.082830,52.422020,0\n" +
            "13.112840,52.440320,0\n" +
            "13.098610,52.451260,0\n" +
            "13.110370,52.480480,0\n" +
            "13.154930,52.507990,0\n" +
            "13.139770,52.515140,0\n" +
            "13.111610,52.512130,0\n" +
            "13.111920,52.536890,0\n" +
            "13.125530,52.558720,0\n" +
            "13.139150,52.557780,0\n" +
            "13.146270,52.577710,0\n" +
            "13.129860,52.575270,0\n" +
            "13.120270,52.582040,0\n" +
            "13.122130,52.590310,0\n" +
            "13.143790,52.595200,0\n" +
            "13.150980,52.600130,0\n" +
            "13.166670,52.602090,0\n" +
            "13.207980,52.591370,0\n" +
            "13.192960,52.606580,0\n" +
            "13.215490,52.631340,0\n" +
            "13.258230,52.631770,0\n" +
            "13.256090,52.643060,0\n" +
            "13.278260,52.644580,0\n" +
            "13.276830,52.663450,0\n" +
            "13.309380,52.662370,0\n" +
            "13.315110,52.656940,0\n" +
            "13.306880,52.652610,0\n" +
            "13.314390,52.643060,0\n" +
            "13.314030,52.630900,0\n" +
            "13.338710,52.625910,0\n" +
            "13.368400,52.629600,0\n" +
            "13.382170,52.640020,0\n" +
            "13.392190,52.649780,0\n" +
            "13.399700,52.649780,0\n" +
            "13.415700,52.644750,0\n" +
            "13.426900,52.639620,0\n" +
            "13.436110,52.651550,0\n" +
            "13.461620,52.652230,0\n" +
            "13.462620,52.654910,0\n" +
            "13.445200,52.662000,0\n" +
            "13.457390,52.672110,0\n" +
            "13.464610,52.670830,0\n" +
            "13.473190,52.678300,0\n" +
            "13.492610,52.671660,0\n" +
            "13.489990,52.659210,0\n" +
            "13.528130,52.646230,0\n" +
            "13.522230,52.627190,0\n" +
            "13.510050,52.623820,0\n" +
            "13.502160,52.606300,0\n" +
            "13.510410,52.594550,0\n" +
            "13.528500,52.595420,0\n" +
            "13.553050,52.590250,0\n" +
            "13.585310,52.572720,0\n" +
            "13.591760,52.551480,0\n" +
            "13.641390,52.544180,0\n" +
            "13.638880,52.533060,0\n" +
            "13.660030,52.532840,0\n" +
            "13.663430,52.524990,0\n" +
            "13.642560,52.514310,0\n" +
            "13.633780,52.493150,0\n" +
            "13.617110,52.478530,0\n" +
            "13.627150,52.475910,0\n" +
            "13.643990,52.482020,0\n" +
            "13.668180,52.476340,0\n" +
            "13.692730,52.467280,0\n" +
            "13.700070,52.472740,0\n" +
            "13.720140,52.464990,0\n" +
            "13.731790,52.454400,0\n" +
            "13.761360,52.448830,0\n" +
            "13.761000,52.438680,0\n" +
            "13.750960,52.438350,0\n" +
            "13.736990,52.414640,0\n" +
            "13.744150,52.409120,0\n" +
            "13.730960,52.394550,0";

    @PostConstruct
    public void init() {
        polygonModel = new DefaultMapModel();
        Polygon polygon = new Polygon();
        List<LatLng> geos = convertKMLCoordinatesToGeoLocations(germanBorderCoordinates, " ");
        for (LatLng geo : geos) {
            System.out.println("Latitude: " + geo.getLat() + "\nLongitude: " + geo.getLng());
            polygon.getPaths().add(geo);
        }
        polygon.setStrokeColor("#adadad");
        polygon.setFillColor("#c6c6c6");
        polygon.setStrokeOpacity(0.7);
        polygon.setFillOpacity(0.7);
        polygonModel.addOverlay(polygon);
    }

    public String getInput() {
        String inputData = "";
        BufferedReader reader;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("tweets.txt");
        reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line = reader.readLine();
            while (line != null) {
                inputData += line + '\n';
                line = reader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return inputData;
    }

    public String startJob() {
        jobOperator = BatchRuntime.getJobOperator();
        execID = jobOperator.start("tweetimport", null);
        return "jobstarted";
    }

    public String getJobStatus() {
        System.out.println(execID);
        return jobOperator.getJobExecution(execID).getBatchStatus().toString();
    }

    public void activeTweetCursor() {
        CursoredStream cs = tweetDao.getAll();
        System.out.println("Number of objects that can be read without blocking: " + cs.available());
        System.out.println("Check if the cursor is at its end: " + cs.atEnd());
        int counter = 0;
        while(cs.hasNext()) {
            counter++;
            System.out.println("Current position: " + cs.getPosition()
                    + "\nTweet: " + ((Tweet) cs.read()).getId()
                    + "\nAvailable tweets: " + cs.available()
                    + "\nCounter: " + counter);
            if (counter == 10) {
                System.out.printf("Finished 10 tweets: " + counter);
                counter = 0;
            }
            if (cs.atEnd()) {
                System.out.println("Im finished bro!");
            }
        }
    }

    public boolean isCompleted() {
        return (getJobStatus().compareTo("COMPLETED") == 0);
    }

    private List<LatLng> convertKMLCoordinatesToGeoLocations(String polygon, String splitCondition) {
        List<LatLng> geoLocations = new ArrayList<>();
        String[] markers = polygon.split(splitCondition);
        for (String marker : markers) {
            String[] coordinates = marker.split(",");
            double longitude = Double.parseDouble(coordinates[0]);
            double latitude = Double.parseDouble(coordinates[1]);
            LatLng geoLocation = new LatLng(latitude, longitude);
            geoLocations.add(geoLocation);
        }
        return geoLocations;
    }

    public MapModel getPolygonModel() {
        return polygonModel;
    }

    public void setPolygonModel(MapModel polygonModel) {
        this.polygonModel = polygonModel;
    }

    public void onRedrawMap(ActionEvent event) {
        polygonModel.getPolygons().clear();
        Polygon polygon = new Polygon();
        List<LatLng> geos = convertKMLCoordinatesToGeoLocations(berlinPolygon, "\n");
        for (LatLng geo : geos) {
            System.out.println("Latitude: " + geo.getLat() + "\nLongitude: " + geo.getLng());
            polygon.getPaths().add(geo);
        }
        polygon.setStrokeColor("#adadad");
        polygon.setFillColor("#c6c6c6");
        polygon.setStrokeOpacity(0.7);
        polygon.setFillOpacity(0.7);
        polygonModel.addOverlay(polygon);
    }

    public void onRedrawMapGermany(ActionEvent event) {
        polygonModel.getPolygons().clear();
        Polygon polygon = new Polygon();
        List<LatLng> geos = convertKMLCoordinatesToGeoLocations(germanBorderCoordinates, " ");
        for (LatLng geo : geos) {
            System.out.println("Latitude: " + geo.getLat() + "\nLongitude: " + geo.getLng());
            polygon.getPaths().add(geo);
        }
        polygon.setStrokeColor("#adadad");
        polygon.setFillColor("#c6c6c6");
        polygon.setStrokeOpacity(0.7);
        polygon.setFillOpacity(0.7);
        polygonModel.addOverlay(polygon);
    }
}
