package com.example.reactivemongodemo.web;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {

    public final String API_V3 = "/api/v3";

    public final String EVALRUN = API_V3 + "/evaluationRun";
    public final String EVALRUN_BY_ID = EVALRUN + "/{evalRunId}";

    public final String P_EVALRUN_ID = "evalRunId";

}
