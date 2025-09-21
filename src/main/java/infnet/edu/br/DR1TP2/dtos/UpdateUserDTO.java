package infnet.edu.br.DR1TP2.dtos;

import java.util.Optional;

public record UpdateUserDTO (Integer id, Optional<String> username, Optional<String> email) {
}
