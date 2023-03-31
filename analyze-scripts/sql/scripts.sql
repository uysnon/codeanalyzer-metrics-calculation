select count(*) as k11
from calculated_data
where number BETWEEN 0 and 11000;
select count(*) as k22
from calculated_data
where number BETWEEN 10001 and 22000;
select count(*) as k33
from calculated_data
where number BETWEEN 22001 and 33000;
select count(*) as k44
from calculated_data
where number BETWEEN 33001 and 44000;
select count(*) as k55
from calculated_data
where number BETWEEN 44001 and 55000;
select count(*) as alll
from calculated_data;

select sum("LOC")
from calculated_data


select *
from calculated_data


select avg("SCORE")
from calculated_data

select max("SCORE")
from calculated_data score: (19.36 / 17.69 + 1) / 2


drop view  public.calculated_data_standard;
create view public.calculated_data_standard as
select 1 - ((((("SCORE") - 19.36) / 17.69) + 1) / 2)                 as score,
       ("LOC" - 12216.50) / 41282.15                                 as loc,
       ("AVG_METHOD_LENGTH" - 12.82) / 54.71                         as avg_m_l,
       ("AVG_DEPENDENCY_TREE_DEPTH")                                 as avg_dt_l,
       ("MAX_DEPENDENCY_TREE_DEPTH")                                 as max_dt_l,
       ("CODE_SMELLS_COUNT" - 423.10) / 1812                         as c_smells,
       "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_15"          as m_l_15,
       "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_15"          as m_m_15,
       "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_30"          as m_m_30,
       "PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_60"          as m_m_60,
       "PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_0_10"                   as abc_0_10,
       "PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_11_999"                 as abc_11_999,
       "PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_21_999"                 as abc_21_999,
       ("PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_41_999" - 0.14) / 0.17 as abc_41_999,
       "PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_0_999"           as cc_0_999,
       "PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_11_999"          as cc_11_999,
       "PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_21_999"          as cc_21_999,
       "PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_41_999"          as cc_31_999
from calculated_data;

-- вычислние персентилей score
p * N / 100




select *
from calculated_data_standard
