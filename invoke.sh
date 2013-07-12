mvn-run org.systemsbiology.athero.WorkflowHost
mvn-run org.systemsbiology.athero.ActivityHost

data_basename=1047-COPD.10K

# clutch:
dir=/mnt/price1/vcassen/Nof1/data/test_rnaseq/rawdata
mvn-run org.systemsbiology.athero.RnaseqPipeline $data_basename $dir

# buffy:
dir=/local/vcassen/Nof1/data/rawdata