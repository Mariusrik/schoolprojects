class User < ApplicationRecord
  has_many :ratings
  has_many :comments
  before_save :encrypt_password
  mount_uploader :image, UserUploader
  validates_confirmation_of :password_digest, message: 'Password must match!'
  validates_presence_of :password_digest, :on => :create
  validates_presence_of :email, :name
  validates_uniqueness_of :email
  validates_format_of :email, :with =>  /\A[^@\s]+@([^@\s]+\.)+[^@\s]+\z/
  validates_format_of :password_digest, :with => /(?=.*[a-zA-Z])(?=.*[0-9]).{8,}/, on: :create, message: 'Your password must contain at least one number and be 8 or more characters long.'
  validates_length_of :name, :maximum => 50
  validates_length_of :email, :maximum => 75

  def self.authenticate(email, password)
    user = find_by_email(email)
    if user && user.password_digest == BCrypt::Engine.hash_secret(password, user.password_salt)
      user
    else
      nil
    end
  end


  private
  def encrypt_password
    if password_digest.present?
      self.password_salt = BCrypt::Engine.generate_salt
      self.password_digest = BCrypt::Engine.hash_secret(password_digest, password_salt)

    end
  end

end
