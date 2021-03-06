<%@ page import="com.model.User" %><%--
  Created by IntelliJ IDEA.
  User: deegii
  Date: 4/21/2018
  Time: 2:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<%
  User user = (User) request.getSession().getAttribute("user");
  if(user == null){
      response.sendRedirect("login.jsp");
  } else {
      String name = user.getName();
%>
<body>
<div class="container">
  <div class="header">
      <div class="jumbotron">
          <a class="btn btn-light logout" href='<% if(session.getAttribute("user") != null){ session.setMaxInactiveInterval(1); out.print("login.jsp");} %>' role="button" id="logout"><i class="fas fa-sign-out-alt"></i>Sign out</a>
          <img src="resources/images/logo.png" width="130px" class="float-left logo">
          <h1 class="display-4">Welcome <span class="user_name"><%=name%></span>, to Cycling Club! </h1>
          <p class="lead">Go ride a bike!</p>
          <hr class="my-4">
          <p>The MIT cycling club is an organization dedicated to encouraging the enjoyment of all types of cycling in the MIT community. We welcome members of the MIT community and friends to most of our club events such as group rides, classes, and community service. </p>
          <p class="lead">
              <a class="btn btn-success" href="#" role="button" id="addEvent"><i class="fas fa-plus"></i>Add Event</a>
              <a class="btn btn-secondary" href="#" role="button" id="alertEvent"><i class="fas fa-bell"></i>Red Box</a>
          </p>
          <div class="add-event">
              <form class="row">
                  <div class="col-sm-6">
                      <div class="form-group row">
                          <label for="eventName" class="col-sm-4 col-form-label">Event name:</label>
                          <div class="col-sm-6">
                            <input type="text" id="eventName" name="eventName" placeholder="Event name" class="form-control" required/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="file" class="col-sm-4 col-form-label">Event image:</label>
                          <div class="custom-file col-sm-6">
                              <input type="file" class="custom-file-input" id="file" name="file"/>
                              <label class="custom-file-label" for="file">Choose file</label>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="start" class="col-sm-4 col-form-label">Start date:</label>
                          <div class="col-sm-6">
                            <input type="datetime-local" id="start" name="start" placeholder="Start date" class="form-control" required/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="end" class="col-sm-4 col-form-label">End date:</label>
                          <div class="col-sm-6">
                                <input type="datetime-local" id="end" name="end" placeholder="End date" class="form-control" required/>
                          </div>
                      </div>
                  </div>
                  <fieldset class="form-group col-sm-6">
                      <legend>Routes</legend>
                      <div class="form-group row">
                          <label for="startPosLong" class="col-sm-4 col-form-label">Start position:</label>
                          <div class="col-sm-3">
                              <input type="text" id="startPosLong" name="startPositionLong" class="form-control"/>
                          </div>
                          <div class="col-sm-3">
                              <input type="text" id="startPosLat" name="startPositionLat" class="form-control"/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="endPosLong" class="col-sm-4 col-form-label">End position:</label>
                          <div class="col-sm-3">
                              <input type="text" id="endPosLong" name="endPositionLong" class="form-control"/>
                          </div>
                          <div class="col-sm-3">
                              <input type="text" id="endPosLat" name="endPositionLot" class="form-control"/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="duration" class="col-sm-4 col-form-label">Duration:</label>
                          <div class="col-sm-6">
                            <input type="number" id="duration" name="duration" placeholder="Duration" class="form-control"/>
                          </div>
                      </div>
                      <input type="button" id="btnAddRoute" value="Save route" class="btn btn-light"/>
                      <div id="dynamicTable" style="margin-top: 20px"></div>
                  </fieldset>
              </form>
              <div class="text-center" >
                  <input type="button" id="btnAddEvent" value="Save event" class="btn btn-primary"/>
              </div>
          </div>
          <div class="alert1" role="alert">

          </div>
      </div>
  </div>
  <div class="content">
    <ul class="nav nav-tabs">
      <li class="active"><a class="nav-link active" data-toggle="tab" href="#upcoming">Upcoming rides</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#ongoing">Live Cycling Rides</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#members">Members</a></li>
    </ul>

    <div class="tab-content">
      <div id="upcoming" class="tab-pane fade show active">
        <div class="card-columns" id="columns"></div>
      </div>
      <div id="ongoing" class="tab-pane fade">
          <div class="card-columns" id="columns1"></div>
      </div>
      <div id="members" class="tab-pane fade">
          <div class="card-columns" id="columns2"></div>
      </div>
    </div>

  </div>
</div>
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <%--<button type="button" class="btn btn-primary" id="">Join a ride</button>--%>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%
    }
%>