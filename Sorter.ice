#pragma once

module Demo {
    sequence<int> IntArray;

    class SortResult {
        IntArray data;
    };

    interface Worker {
        IntArray sort(IntArray data);
    };

    interface Coordinator {
        void registerWorker(Worker* worker);
        SortResult sortData(IntArray data);
    };
};