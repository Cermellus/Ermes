/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CallLogUpdateComponent } from 'app/entities/call-log/call-log-update.component';
import { CallLogService } from 'app/entities/call-log/call-log.service';
import { CallLog } from 'app/shared/model/call-log.model';

describe('Component Tests', () => {
    describe('CallLog Management Update Component', () => {
        let comp: CallLogUpdateComponent;
        let fixture: ComponentFixture<CallLogUpdateComponent>;
        let service: CallLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogUpdateComponent]
            })
                .overrideTemplate(CallLogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CallLogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CallLogService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CallLog(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.callLog = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CallLog();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.callLog = entity;
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
