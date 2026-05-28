FROM postgres:12-alpine

# Copy our custom pg_hba.conf before database initialization
# It will be used during server startup
COPY pg_hba.conf /pg_hba.conf.initial

# Create initialization script that installs the custom pg_hba.conf
RUN mkdir -p /docker-entrypoint-initdb.d && \
    echo '#!/bin/bash' > /docker-entrypoint-initdb.d/00-setup-hba.sh && \
    echo 'set -e' >> /docker-entrypoint-initdb.d/00-setup-hba.sh && \
    echo 'echo "Setting up pg_hba.conf with custom configuration..."' >> /docker-entrypoint-initdb.d/00-setup-hba.sh && \
    echo 'cp /pg_hba.conf.initial "$PGDATA/pg_hba.conf"' >> /docker-entrypoint-initdb.d/00-setup-hba.sh && \
    echo 'chmod 600 "$PGDATA/pg_hba.conf"' >> /docker-entrypoint-initdb.d/00-setup-hba.sh && \
    echo 'echo "pg_hba.conf is ready"' >> /docker-entrypoint-initdb.d/00-setup-hba.sh && \
    chmod +x /docker-entrypoint-initdb.d/00-setup-hba.sh



