import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Router } from '@angular/router';

describe('LoginComponent (Integration)', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: { login: jest.Mock };
  let sessionServiceMock: { logIn: jest.Mock, logOut: jest.Mock, isLogged: boolean, sessionInformation: any, $isLogged: jest.Mock };
  let routerMock: { navigate: jest.Mock, events: any, routerState: any, errorHandler: jest.Mock, isActive: jest.Mock };

  beforeEach(async () => {
    // Création d'un mock pour AuthService
    authServiceMock = {
      login: jest.fn().mockReturnValue(of({
        token: 'fake-token',
        type: 'Bearer',
        id: 1,
        username: 'testuser',
        firstName: 'Test',
        lastName: 'User',
        admin: false
      }))
    };

    // Création d'un mock pour SessionService
    sessionServiceMock = {
      logIn: jest.fn(),
      logOut: jest.fn(),
      isLogged: false,
      sessionInformation: undefined,
      $isLogged: jest.fn()
    };

    // Création d'un mock pour Router
    routerMock = {
      navigate: jest.fn(),
      events: of({}),
      routerState: {},
      errorHandler: jest.fn(),
      isActive: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        RouterTestingModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the login component', () => {
    expect(component).toBeTruthy();
  });

  it('should login successfully and navigate to /sessions', () => {
    // Remplir le formulaire avec des données valides
    component.form.setValue({
      email: 'testuser@example.com',
      password: 'password123'
    });

    // Soumettre le formulaire
    component.submit();

    // Vérifier que le service de session a été appelé pour la connexion
    expect(sessionServiceMock.logIn).toHaveBeenCalled();

    // Vérifier que la navigation a eu lieu vers /sessions
    expect(routerMock.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should show an error when login fails', () => {
    // Simuler une erreur lors de la connexion
    authServiceMock.login.mockReturnValue(throwError(() => new Error('Login failed')));

    // Remplir le formulaire avec des données valides
    component.form.setValue({
      email: 'testuser@example.com',
      password: 'password123'
    });

    // Soumettre le formulaire
    component.submit();

    // Vérifier que l'erreur est gérée correctement
    expect(component.onError).toBe(true);
  });

  it('should validate the form before submitting', () => {
    // Soumettre le formulaire sans données
    component.submit();

    // Vérifier que le formulaire a des erreurs
    expect(component.form.valid).toBeFalsy();
  });
});
