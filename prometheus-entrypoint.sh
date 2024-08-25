#!/bin/sh
set -ex

echo "Current APP_HOST: ${APP_HOST}"

sed "s/\${APP_HOST}/${APP_HOST}/g" /etc/prometheus/prometheus.yml.template > /etc/prometheus/prometheus.yml

echo "Contents of prometheus.yml after substitution:"
cat /etc/prometheus/prometheus.yml

exec /bin/prometheus "$@"