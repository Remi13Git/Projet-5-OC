describe('Details Session spec', () => {
    it('should view the session details', () => {

      // 1. Test de connexion réussie
      cy.visit('/login')
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'Teacher',
          firstName: 'New',
          lastName: 'User',
          admin: true,
        },
      }).as('loginRequest')
      const sessions = [
        { id: 1, name: 'Yoga Session', description: 'Relaxation and stretching', time: '10:00 AM' },
        { id: 2, name: 'Pilates Session', description: 'Core strengthening', time: '02:00 PM' },
      ]
      cy.intercept('GET', '/api/session', {
        statusCode: 200,
        body: sessions,
      }).as('sessionsRequest')
      const teachers = [
        {
          id: 1,
          lastName: 'User',
          firstName: 'New',
          createdAt: '2023-01-01T00:00:00.000Z',
          updatedAt: '2023-01-01T00:00:00.000Z',
        },
      ]
      cy.intercept('GET', '/api/teacher', {
        statusCode: 200,
        body: teachers,
      }).as('teachersRequest')
      cy.get('input[formControlName=email]').type('newuser@studio.com')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type="submit"]').click()
      cy.wait('@loginRequest')
      cy.url().should('include', '/sessions')
      
  
      // 2. Initialiser la session à récupérer
      const sessionToGet = {
        id: 1,
        name: 'Yoga Session',
        description: 'Relaxation and stretching',
        date: '2025-03-23',
        teacher_id: 1,
      }
      
      // Simuler la récupération de la session avec l'ID 1
      cy.intercept('GET', '/api/session/1', {
        statusCode: 200,
        body: sessionToGet,
      }).as('getSessionToGet')
  
  
      // Accéder à la page de détails de la session avec l'ID 1 (Yoga Session)
      cy.get('mat-card').contains('Detail').click()

    });
});