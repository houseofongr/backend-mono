package com.aoo.admin.application.port.out.soundsource;

import com.aoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.aoo.admin.application.port.in.soundsource.QuerySoundSourceListResult;

public interface QuerySoundSourcePort {
    QuerySoundSourceListResult querySoundSourceList(QuerySoundSourceListCommand command);
}
