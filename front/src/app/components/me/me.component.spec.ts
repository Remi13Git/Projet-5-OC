import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { MeComponent } from './me.component';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userServiceMock: Partial<UserService>;
  let sessionServiceMock: Partial<SessionService>;
  let routerMock: Partial<Router>;
  let matSnackBarMock: Partial<MatSnackBar>;

  const mockUser = { id: 1, name: 'Test User', email: 'test@example.com' };

  // Ajout des propriétés manquantes dans sessionInformation
  const mockSessionInformation = {
    id: 1,
    admin: true,
    token: 'mock-token',
    type: 'user',
    username: 'testuser',
    firstName: 'Test',
    lastName: 'User'
  };

  beforeEach(async () => {
    // Création des mocks pour les services
    userServiceMock = {
      getById: jest.fn().mockReturnValue(of(mockUser)),
      delete: jest.fn().mockReturnValue(of(null)),
    };

    sessionServiceMock = {
      sessionInformation: mockSessionInformation,
      logOut: jest.fn(),
    };

    routerMock = {
      navigate: jest.fn(),
    };

    matSnackBarMock = {
      open: jest.fn(),
    };

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call userService.getById in ngOnInit and assign user', () => {
    component.ngOnInit();
    expect(userServiceMock.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(mockUser);
  });

  it('should call window.history.back() when back() is called', () => {
    const spyBack = jest.spyOn(window.history, 'back');
    component.back();
    expect(spyBack).toHaveBeenCalled();
  });

  it('should delete user and log out when delete() is called', () => {
    component.delete();
    expect(userServiceMock.delete).toHaveBeenCalledWith('1');
    expect(matSnackBarMock.open).toHaveBeenCalledWith(
      'Your account has been deleted !',
      'Close',
      { duration: 3000 }
    );
    expect(sessionServiceMock.logOut).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
  });
});
