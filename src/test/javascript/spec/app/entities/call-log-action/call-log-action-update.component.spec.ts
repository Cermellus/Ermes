/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CallLogActionUpdateComponent } from 'app/entities/call-log-action/call-log-action-update.component';
import { CallLogActionService } from 'app/entities/call-log-action/call-log-action.service';
import { CallLogAction } from 'app/shared/model/call-log-action.model';

describe('Component Tests', () => {
    describe('CallLogAction Management Update Component', () => {
        let comp: CallLogActionUpdateComponent;
        let fixture: ComponentFixture<CallLogActionUpdateComponent>;
        let service: CallLogActionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogActionUpdateComponent]
            })
                .overrideTemplate(CallLogActionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CallLogActionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogActionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CallLogAction(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.callLogAction = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CallLogAction();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.callLogAction = entity;
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
