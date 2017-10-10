class UserUploader < CarrierWave::Uploader::Base

include CarrierWave::MiniMagick

  # Choose what kind of storage to use for this uploader:
  # storage :file         # Used for local storing
  storage :fog    # Used for cloudstoring, either on Google Cloud or Amazon S3. In our case Google Cloud

  # Override the directory where uploaded files will be stored.
  # This is a sensible default for uploaders that are meant to be mounted:
  def store_dir
    "uploads/#{model.class.to_s.underscore}/#{mounted_as}/#{model.id}"
  end

  # Provide a default URL as a default if there hasn't been a file uploaded:
  def default_url#(*args)
    ActionController::Base.helpers.asset_path("fallback/user/" + [version_name, "default.png"].compact.join('_'))
  end

  # Create different versions of your uploaded files:
  version :thumb do
    process resize_to_fit: [30, 30]
  end

  version :large do
    process resize_to_fit: [200, 200]
  end

  # Add a white list of extensions which are allowed to be uploaded.
  # For images you might use something like this:
  def extension_whitelist
    %w(jpg jpeg gif png)
  end

end