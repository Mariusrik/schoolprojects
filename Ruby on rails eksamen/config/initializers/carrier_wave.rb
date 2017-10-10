require 'carrierwave/orm/activerecord'

CarrierWave.configure do |config|
  config.fog_provider = 'fog/google'                        # required
  config.fog_credentials = {
      provider:                         'Google',
      google_storage_access_key_id:     'GOOGPJRH353G2A3YSD7P',
      google_storage_secret_access_key: 'IP+yB1m1MoEq4kP1hsOYVu8AChwQkarHiu9i18MZ'
  }
  config.fog_directory = 'pg4300grp10b'
end