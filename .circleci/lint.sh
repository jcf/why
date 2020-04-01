#!/bin/sh
exec clj-kondo --lint \
  dev dev-resources resources src test
