import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICallLog } from 'app/shared/model/call-log.model';
import { CallLogService } from './call-log.service';

@Component({
    selector: 'jhi-call-log-delete-dialog',
    templateUrl: './call-log-delete-dialog.component.html'
})
export class CallLogDeleteDialogComponent {
    callLog: ICallLog;

    constructor(protected callLogService: CallLogService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.callLogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'callLogListModification',
                content: 'Deleted an callLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-call-log-delete-popup',
    template: ''
})
export class CallLogDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ callLog }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CallLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.callLog = callLog;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/call-log', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/call-log', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
