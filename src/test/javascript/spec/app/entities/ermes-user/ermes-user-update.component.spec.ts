/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { ErmesUserUpdateComponent } from 'app/entities/ermes-user/ermes-user-update.component';
import { ErmesUserService } from 'app/entities/ermes-user/ermes-user.service';
import { ErmesUser } from 'app/shared/model/ermes-user.model';

describe('Component Tests', () => {
    describe('ErmesUser Management Update Component', () => {
        let comp: ErmesUserUpdateComponent;
        let fixture: ComponentFixture<ErmesUserUpdateComponent>;
        let service: ErmesUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [ErmesUserUpdateComponent]
            })
                .overrideTemplate(ErmesUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ErmesUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ErmesUserService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ErmesUser(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ermesUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ErmesUser();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.ermesUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
