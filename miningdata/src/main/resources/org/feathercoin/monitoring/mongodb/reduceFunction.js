function(key, values){
    print("test");print(key);print(values);
    maxValue = 0;
    for (i=0;i<values.length;i++){
        if (values[i]>maxValue){
            maxValue = values[i];
        }
    }
    print(maxValue ); return maxValue
}