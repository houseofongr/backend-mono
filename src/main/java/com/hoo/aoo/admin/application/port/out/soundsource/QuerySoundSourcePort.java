package com.hoo.aoo.admin.application.port.out.soundsource;

import com.hoo.aoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.aoo.admin.application.port.in.soundsource.QuerySoundSourceListResult;

public interface QuerySoundSourcePort {
    QuerySoundSourceListResult querySoundSourceList(QuerySoundSourceListCommand command);
}
