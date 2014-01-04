function (key, values) {
    maxValue = values[0].fetchTime;
    returnIndex = 0;
    for (i = 0; i < values.length; i++) {
        currentTime = values[i].fetchTime;
        if ((maxValue - currentTime) < 0) {
            maxValue = currentTime;
            returnIndex = i;
        }
    }
    values[0].fetchTime = maxValue;
    print(maxValue);
    return values[returnIndex];
}