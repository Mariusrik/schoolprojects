class ForSalesController < ApplicationController
  before_action :set_for_sale, only: [:show, :edit, :update, :destroy]
  skip_before_action :require_login, :only=>[:show]

  # GET /for_sales
  # GET /for_sales.json
  def index
    @for_sales = ForSale.all
  end

  # GET /for_sales/1
  # GET /for_sales/1.json
  def show
  end

  # GET /for_sales/new
  def new
    @for_sale = ForSale.new
  end

  # GET /for_sales/1/edit
  def edit
    require_same_user(@for_sale.user_id)
  end

  # POST /for_sales
  # POST /for_sales.json
  def create
    @for_sale = ForSale.new(for_sale_params)
    @for_sale.user_id = current_user.id

    respond_to do |format|
      if @for_sale.save
        format.html { redirect_to @for_sale, success: 'For sale was successfully created.' }
        format.json { render :show, status: :created, location: @for_sale }
      else
        format.html { render :new }
        format.json { render json: @for_sale.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /for_sales/1
  # PATCH/PUT /for_sales/1.json
  def update
    respond_to do |format|
      if @for_sale.update(for_sale_params)
        format.html { redirect_to @for_sale, success: 'For sale was successfully updated.' }
        format.json { render :show, status: :ok, location: @for_sale }
      else
        format.html { render :edit }
        format.json { render json: @for_sale.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /for_sales/1
  # DELETE /for_sales/1.json
  def destroy
    @for_sale.destroy
    respond_to do |format|
      format.html { redirect_to for_sales_url, info: 'For sale was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_for_sale
      @for_sale = ForSale.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def for_sale_params
      params.require(:for_sale).permit(:price, :contact, :user_id, :book_id)
    end
end
