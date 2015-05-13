_Warning_: `Verbose GC Analyzer` is quite old. It has been tested with Java 1.6 and needs to be updated to work with more recents JDKs.

#Introduction

`Verbose GC Analyzer` is a tool to generate a CSV export and a sum-up report of Java GC logs.
mine Java GC logs and to generate :
* a sum-up report
* an HTML report with charts for young size, tenured size, promoted size, collection pause time and cpu wall time
* a CSV export

#Releases

* [VerboseGCAnalyzer-1.3-bin.zip](http://code.google.com/p/verbosegcanalyzer/downloads/detail?name=VerboseGCAnalyzer-1.3-bin.zip)

#Compatibility

Works only for  with the following JVM arguments `-verbosegc -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Xloggc:logs/gc.log` and CMS collector.
Tested with JDK 6.

#Sum-up report example

```
Total time: 20.51 min
Young size collected: 1456 GB
Young size collected: 1212.274 MB/s
Total size collected by minor GC: 1446 GB
Total size collected by minor GC: 1203.75 MB/s
Promoted size: 10 GB
Promoted size: 8.523 MB/s
Cpu Minor GC real: 4.9%
Cpu Major GC total processors: 55.87%
Cpu Major GC real: 3.6%
Cpu Major GC total processors: 32.26%
```

#HTML report example

![Tenured size](/VerboseGCAnalyzer/media/tenured_size.jpg)

![Young size](/VerboseGCAnalyzer/media/young_size.jpg)

![Promoted size](/VerboseGCAnalyzer/media/promoted_size.jpg)

![Collection pause time](/VerboseGCAnalyzer/media/collection_pause_time.jpg)

![CPU real](/VerboseGCAnalyzer/media/cpu_real.jpg)

#CSV export example

```
date,time,type,cpuReal,cpuUser,cpuSystem,collectionPauseTime,youngSizeBefore,youngSizeAfter,youngSizeMax,totalSizeBefore,totalSizeAfter,totalSizeMax,youngSizeCollected,totalSizeCollected,promotedSize,tenuredSizeBefore,tenuredSizeAfter
12-04-12 12:32:14,1495.246,PAR_NEW,0.2,2.07,0.07,0.191013,2716018,283763,2831168,2719113,304590,5976896,2432255,2414523,17732,3095,20827
12-04-12 12:32:17,1498.813,PAR_NEW,0.17,1.68,0.06,0.171789,2800371,270531,2831168,2821198,351127,5976896,2529840,2470071,59769,20827,80596
12-04-12 12:32:20,1501.483,PAR_NEW,0.12,1.27,0.02,0.114284,2787139,196920,2831168,2867735,308497,5976896,2590219,2559238,30981,80596,111577
12-04-12 12:32:38,1519.563,PAR_NEW,0.15,1.55,0.04,0.149614,2713528,175844,2831168,2825105,343742,5976896,2537684,2481363,56321,111577,167898
12-04-12 12:32:43,1524.376,PAR_NEW,0.14,1.59,0.03,0.14533,2692452,228574,2831168,2860350,443592,5976896,2463878,2416758,47120,167898,215018
12-04-12 12:32:46,1526.858,PAR_NEW,0.13,1.61,0.01,0.132943,2745182,314560,2831168,2960200,533091,5976896,2430622,2427109,3513,215018,218531
12-04-12 12:32:48,1528.855,PAR_NEW,0.16,1.58,0.08,0.164366,2831168,209028,2831168,3049699,529607,5976896,2622140,2520092,102048,218531,320579
12-04-12 12:32:55,1536.682,PAR_NEW,0.02,0.26,0.0,0.022403,2725636,91050,2831168,3046215,411629,5976896,2634586,2634586,0,320579,320579
12-04-12 12:33:03,1544.147,PAR_NEW,0.03,0.39,0.01,0.033644,2607658,85867,2831168,2928237,406447,5976896,2521791,2521790,1,320579,320580
12-04-12 12:33:07,1548.219,PAR_NEW,0.03,0.34,0.02,0.035601,2602475,54016,2831168,2923055,403462,5976896,2548459,2519593,28866,320580,349446
12-04-12 12:33:10,1551.612,PAR_NEW,0.02,0.22,0.0,0.022695,2570624,25121,2831168,2920070,382791,5976896,2545503,2537279,8224,349446,357670
12-04-12 12:33:17,1558.765,PAR_NEW,0.02,0.2,0.0,0.017609,2541729,20322,2831168,2899399,379813,5976896,2521407,2519586,1821,357670,359491
12-04-12 12:33:22,1563.786,PAR_NEW,0.03,0.26,0.0,0.025393,2536930,34415,2831168,2896421,397325,5976896,2502515,2499096,3419,359491,362910
12-04-12 12:33:27,1568.02,PAR_NEW,0.02,0.23,0.0,0.020401,2551023,33077,2831168,2913933,396887,5976896,2517946,2517046,900,362910,363810
12-04-12 12:33:30,1571.37,PAR_NEW,0.02,0.12,0.0,0.012661,2549685,24826,2831168,2913495,389373,5976896,2524859,2524122,737,363810,364547
[...]
12-04-12 12:52:06,2687.4,PAR_NEW,0.09,0.96,0.0,0.087718,2700042,186253,2831168,4957222,2466909,5976896,2513789,2490313,23476,2257180,2280656
12-04-12 12:52:09,2689.964,PAR_NEW,0.1,1.09,0.01,0.096835,2702861,138697,2831168,4983517,2436045,5976896,2564164,2547472,16692,2280656,2297348
12-04-12 12:52:10,2691.816,PAR_NEW,0.11,1.14,0.0,0.101487,2655305,189954,2831168,4952653,2503400,5976896,2465351,2449253,16098,2297348,2313446
12-04-12 12:52:40,2721.221,PAR_NEW,0.08,0.93,0.01,0.077265,2516608,143541,2831168,3371825,998758,5976896,2373067,2373067,0,855217,855217
12-04-12 12:52:42,2723.044,PAR_NEW,0.12,1.5,0.0,0.120165,2660149,249983,2831168,3515366,1105201,5976896,2410166,2410165,1,855217,855218
12-04-12 12:52:43,2724.449,PAR_NEW,0.14,1.59,0.0,0.142116,2766591,216414,2831168,3621809,1117637,5976896,2550177,2504172,46005,855218,901223
12-04-12 12:52:44,2725.62,PAR_NEW,0.1,1.25,0.01,0.102439,2733022,241279,2831168,3634245,1142502,5976896,2491743,2491743,0,901223,901223
```

#Getting started

 * `wget http://verbosegcanalyzer.googlecode.com/files/VerboseGCAnalyzer-<major>.<inc>-bin.zip`
 * `unzip VerboseGCAnalyzer-<major>.<inc>-bin.zip`
 * `cd VerboseGCAnalyzer-<major>.<inc>/bin`
 * `chmod 775 start.sh`
 * `/start.sh java_gc.log`

#Configuration

* Change `conf/conf.properties` to configure `Verbose GC Analyzer`:

|Key|Default|Comments|
|---|-------|--------|
|dateFormat|yyyy-MM-dd'T'HH:mm:ss.SSSZ|Date format of the Java GC logs|
|exportDateFormat|yy-MM-dd HH:mm:ss|Date format of the CSV export|
|startDate|None|Start date in `exportDateFormat` format to filter the report|
|endDate|None|End date in `exportDateFormat` format to filter the report|
|exportHTMLReport|true||Put false to disable HTML report generation|
|exportCSV|true|Put false to disable CSV export|
|dislayMajorGC|false|Put true to export major GC in CSV|
