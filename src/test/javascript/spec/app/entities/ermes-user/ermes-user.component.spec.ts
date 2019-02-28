/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ErmesTestModule } from '../../../test.module';
import { ErmesUserComponent } from 'app/entities/ermes-user/ermes-user.component';
import { ErmesUserService } from 'app/entities/ermes-user/ermes-user.service';
import { ErmesUser } from 'app/shared/model/ermes-user.model';

describe('Component Tests', () => {
    describe('ErmesUser Management Component', () => {
        let comp: ErmesUserComponent;
        let fixture: ComponentFixture<ErmesUserComponent>;
        let service: ErmesUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [ErmesUserComponent],
                providers: []
            })
                .overrideTemplate(ErmesUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ErmesUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ErmesUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ErmesUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ermesUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
