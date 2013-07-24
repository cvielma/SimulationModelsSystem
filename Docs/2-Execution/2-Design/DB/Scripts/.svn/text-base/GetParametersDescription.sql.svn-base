SELECT p."C_PARAM", p."NM_PARAM", 
       --(SELECT a."VL_TYPE" FROM "ADM_TYPES" as a WHERE a."C_TYPE" = p."VL_STATUS") as "STATUS",
       (SELECT a."VL_TYPE" FROM "ADM_TYPES" as a WHERE a."C_TYPE" = pv."TP_PARAM_VALUE") as "TYPE",
       p."VL_UNIQUE", 
       (SELECT right(a."VL_TYPE", -6)FROM "ADM_TYPES" as a WHERE a."C_TYPE" = pv."VL_MIN_VALUE") as "VL_MIN",
       (SELECT right(a."VL_TYPE", -6)FROM "ADM_TYPES" as a WHERE a."C_TYPE" = pv."VL_MAX_VALUE") as "VL_MAX",
       (SELECT right(a."VL_TYPE", -6)FROM "ADM_TYPES" as a WHERE a."C_TYPE" = pv."VL_DEFAULT") as "VL_DEFAULT",
       pv."C_PARAM_VALUE"
FROM "PARAMETER" as p,
     "PARAMETER_VALUE" as pv
WHERE
     p."C_PARAM" = pv."C_PARAM"
ORDER BY p."C_PARAM", pv."C_PARAM_VALUE"
     