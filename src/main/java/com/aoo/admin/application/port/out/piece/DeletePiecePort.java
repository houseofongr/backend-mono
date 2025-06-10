package com.aoo.admin.application.port.out.piece;

import java.util.List;

public interface DeletePiecePort {
    void deleteAll(List<Long> ids);
    void delete(Long id);
}
