package br.com.hungry.app.dtos;

import java.util.List;

public record StatusErrorResponse(
        Integer codigo,
        String mensagem,
        List<RestValidationError> detalhes
) {
}
