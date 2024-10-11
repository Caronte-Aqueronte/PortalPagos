CREATE DATABASE portal_pagos;
CREATE USER 'usuario_portal_pagos' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON portal_pagos.* TO 'usuario_portal_pagos';
FLUSH PRIVILEGES;
