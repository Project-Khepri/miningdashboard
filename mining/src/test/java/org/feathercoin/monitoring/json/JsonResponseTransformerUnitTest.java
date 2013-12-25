package org.feathercoin.monitoring.json;

import org.feathercoin.monitoring.dto.*;
import org.junit.Before;
import org.junit.Test;

public class JsonResponseTransformerUnitTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTransformSummary() throws Exception {
        JsonResponseTransformer<SummaryResponse> transformer = new JsonResponseTransformer<SummaryResponse>(SummaryResponse.class);
        SummaryResponse SummaryResponse = transformer.transform("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1387267830,\"Code\":11,\"Msg\":\"Summary\",\"Description\":\"cgminer 3.6.6\"}],\"SUMMARY\":[{\"Elapsed\":2906,\"MHS av\":1.87,\"MHS 5s\":1.89,\"Found Blocks\":0,\"Getworks\":85,\"Accepted\":418,\"Rejected\":9,\"Hardware Errors\":2,\"Utility\":8.63,\"Discarded\":138,\"Stale\":0,\"Get Failures\":0,\"Local Work\":511,\"Remote Failures\":0,\"Network Blocks\":22,\"Total MH\":5431.6237,\"Work Utility\":1765.61,\"Difficulty Accepted\":88784.00000000,\"Difficulty Rejected\":2432.00000000,\"Difficulty Stale\":0.00000000,\"Best Share\":236617,\"Device Hardware%\":0.0023,\"Device Rejected%\":2.8437,\"Pool Rejected%\":2.6662,\"Pool Stale%\":0.0000}],\"id\":1}");
        System.out.println(SummaryResponse);

    }

    @Test
    public void testTransformVersion() throws Exception {
        JsonResponseTransformer<VersionResponse> transformer = new JsonResponseTransformer<VersionResponse>(VersionResponse.class);
        VersionResponse versionResponse = transformer.transform("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1387057701,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 3.6.6\"}],\"VERSION\":[{\"CGMiner\":\"3.6.6\",\"API\":\"1.32\"}],\"id\":1}");
        System.out.println(versionResponse);

    }

    @Test
    public void testTransformConfig() throws Exception {
        JsonResponseTransformer<ConfigResponse> transformer = new JsonResponseTransformer<ConfigResponse>(ConfigResponse.class);
        ConfigResponse configResponse = transformer.transform("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1387058524,\"Code\":33,\"Msg\":\"CGMiner config\",\"Description\":\"cgminer 3.6.6\"}],\"CONFIG\":[{\"GPU Count\":4,\"ASC Count\":0,\"PGA Count\":0,\"Pool Count\":2,\"ADL\":\"Y\",\"ADL in use\":\"Y\",\"Strategy\":\"Failover\",\"Log Interval\":5,\"Device Code\":\"GPU BAS BFL BFU ICA AVA MMQ \",\"OS\":\"Linux\",\"Failover-Only\":true,\"ScanTime\":30,\"Queue\":1,\"Expiry\":120,\"Hotplug\":5}],\"id\":1}");
        System.out.println(configResponse);

    }

    @Test
    public void testTransformCoin() throws Exception {
        JsonResponseTransformer<CoinResponse> transformer = new JsonResponseTransformer<CoinResponse>(CoinResponse.class);
        CoinResponse coinResponse = transformer.transform("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1387091474,\"Code\":78,\"Msg\":\"CGMiner coin\",\"Description\":\"cgminer 3.6.6\"}],\"COIN\":[{\"Hash Method\":\"scrypt\",\"Current Block Time\":1387090928.214792,\"Current Block Hash\":\"1e23ffc0f211e0dff3742c2fd93e08629c129e2ed464455764220bd5c2b81ae3\",\"LP\":true,\"Network Difficulty\":11571193.96505999}],\"id\":1}");
        System.out.println(coinResponse);

    }

    @Test
    public void testTransformPools() throws Exception {
        JsonResponseTransformer<PoolResponse> transformer = new JsonResponseTransformer<PoolResponse>(PoolResponse.class);
        PoolResponse poolResponse = transformer.transform("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1387060403,\"Code\":7,\"Msg\":\"2 Pool(s)\",\"Description\":\"cgminer 3.6.6\"}],\"POOLS\":[{\"POOL\":0,\"URL\":\"stratum+tcp://ftc.give-me-coins.com:3336\",\"Status\":\"Alive\",\"Priority\":0,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":315,\"Accepted\":631,\"Rejected\":13,\"Discarded\":614,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"sradics1977.1\",\"Last Share Time\":1387060390,\"Diff1 Shares\":420453,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":427071.00000000,\"Difficulty Rejected\":4542.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":515.00000000,\"Has Stratum\":true,\"Stratum Active\":true,\"Stratum URL\":\"ftc.give-me-coins.com\",\"Has GBT\":false,\"Best Share\":279219,\"Pool Rejected%\":1.0523,\"Pool Stale%\":0.0000},{\"POOL\":1,\"URL\":\"stratum+tcp://pool.d2.cc:3333\",\"Status\":\"Alive\",\"Priority\":1,\"Quota\":1,\"Long Poll\":\"N\",\"Getworks\":1,\"Accepted\":0,\"Rejected\":0,\"Discarded\":0,\"Stale\":0,\"Get Failures\":0,\"Remote Failures\":0,\"User\":\"sradics.2\",\"Last Share Time\":0,\"Diff1 Shares\":0,\"Proxy Type\":\"\",\"Proxy\":\"\",\"Difficulty Accepted\":0.00000000,\"Difficulty Rejected\":0.00000000,\"Difficulty Stale\":0.00000000,\"Last Share Difficulty\":0.00000000,\"Has Stratum\":true,\"Stratum Active\":false,\"Stratum URL\":\"\",\"Has GBT\":false,\"Best Share\":0,\"Pool Rejected%\":0.0000,\"Pool Stale%\":0.0000}],\"id\":1}");
        System.out.println(poolResponse);

    }

    @Test
    public void testTransformDevs() throws Exception {
        JsonResponseTransformer<DevResponse> transformer = new JsonResponseTransformer<DevResponse>(DevResponse.class);
        DevResponse devResponse = transformer.transform("{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1387061687,\"Code\":9,\"Msg\":\"4 GPU(s) - 0 ASC(s) - 0 PGA(s) - \",\"Description\":\"cgminer 3.6.6\"}],\"DEVS\":[{\"GPU\":0,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":80.00,\"Fan Speed\":4228,\"Fan Percent\":83,\"GPU Clock\":1140,\"Memory Clock\":1498,\"GPU Voltage\":1.188,\"GPU Activity\":99,\"Powertune\":20,\"MHS av\":0.68,\"MHS 5s\":0.69,\"Accepted\":172,\"Rejected\":1,\"Hardware Errors\":3,\"Utility\":0.81,\"Intensity\":\"19\",\"Last Share Pool\":0,\"Last Share Time\":1387061682,\"Total MH\":8738.3081,\"Diff1 Work\":117164,\"Difficulty Accepted\":117429.00000000,\"Difficulty Rejected\":549.00000000,\"Last Share Difficulty\":622.00000000,\"Last Valid Work\":1387061682,\"Device Hardware%\":0.0026,\"Device Rejected%\":0.4686,\"Device Elapsed\":12761},{\"GPU\":1,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":66.00,\"Fan Speed\":3351,\"Fan Percent\":84,\"GPU Clock\":1060,\"Memory Clock\":1375,\"GPU Voltage\":1.170,\"GPU Activity\":99,\"Powertune\":20,\"MHS av\":0.64,\"MHS 5s\":0.64,\"Accepted\":155,\"Rejected\":2,\"Hardware Errors\":1,\"Utility\":0.73,\"Intensity\":\"20\",\"Last Share Pool\":0,\"Last Share Time\":1387061637,\"Total MH\":8156.8727,\"Diff1 Work\":112339,\"Difficulty Accepted\":104546.00000000,\"Difficulty Rejected\":774.00000000,\"Last Share Difficulty\":622.00000000,\"Last Valid Work\":1387061637,\"Device Hardware%\":0.0009,\"Device Rejected%\":0.6890,\"Device Elapsed\":12761},{\"GPU\":2,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":74.00,\"Fan Speed\":3398,\"Fan Percent\":97,\"GPU Clock\":1190,\"Memory Clock\":1250,\"GPU Voltage\":1.250,\"GPU Activity\":99,\"Powertune\":20,\"MHS av\":0.70,\"MHS 5s\":0.71,\"Accepted\":200,\"Rejected\":8,\"Hardware Errors\":0,\"Utility\":0.94,\"Intensity\":\"20\",\"Last Share Pool\":0,\"Last Share Time\":1387061581,\"Total MH\":8986.2963,\"Diff1 Work\":134005,\"Difficulty Accepted\":137563.00000000,\"Difficulty Rejected\":3496.00000000,\"Last Share Difficulty\":880.00000000,\"Last Valid Work\":1387061598,\"Device Hardware%\":0.0000,\"Device Rejected%\":2.6089,\"Device Elapsed\":12761},{\"GPU\":3,\"Enabled\":\"Y\",\"Status\":\"Alive\",\"Temperature\":71.00,\"Fan Speed\":3272,\"Fan Percent\":90,\"GPU Clock\":900,\"Memory Clock\":900,\"GPU Voltage\":1.125,\"GPU Activity\":99,\"Powertune\":20,\"MHS av\":0.54,\"MHS 5s\":0.54,\"Accepted\":168,\"Rejected\":4,\"Hardware Errors\":0,\"Utility\":0.79,\"Intensity\":\"19\",\"Last Share Pool\":0,\"Last Share Time\":1387061437,\"Total MH\":6939.4760,\"Diff1 Work\":110278,\"Difficulty Accepted\":114435.00000000,\"Difficulty Rejected\":821.00000000,\"Last Share Difficulty\":880.00000000,\"Last Valid Work\":1387061437,\"Device Hardware%\":0.0000,\"Device Rejected%\":0.7445,\"Device Elapsed\":12761}],\"id\":1}");
        System.out.println(devResponse);

    }
}
