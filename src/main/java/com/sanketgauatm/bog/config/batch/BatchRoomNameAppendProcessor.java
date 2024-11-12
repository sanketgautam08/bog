package com.sanketgauatm.bog.config.batch;

import com.sanketgauatm.bog.model.BatchRoom;
import org.springframework.batch.item.ItemProcessor;

public class BatchRoomNameAppendProcessor implements ItemProcessor<BatchRoom, BatchRoom> {
    @Override
    public BatchRoom process(BatchRoom item) throws Exception {
        item.setName("updated");
        return item;
    }
}
